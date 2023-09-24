package tech.arnav.monawallpapers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.preference.PreferenceManager
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player


class VideoLiveWallpaperService : WallpaperService() {

    override fun onCreateEngine(): WallpaperService.Engine {
        return VideoEngine()
    }

    internal inner class VideoEngine : WallpaperService.Engine(), SensorEventListener {

        private val TAG = javaClass.simpleName
        private val mediaPlayer: ExoPlayer
        private val url: String

        var rotMat = FloatArray(16)
        var vals = FloatArray(3)

        //sensor parallax effect
        private var senSensorManager: SensorManager? = null
        private var senAccelerometer: Sensor? = null
        private val sideVerticalMargin = 0
        private  var sideHorizontalMargin:kotlin.Int = 0
        private val verticalMultiplier = 1f
        private  var horizontalMultiplier:kotlin.Float = 1f

        init {
            Log.i(TAG, "( VideoEngine )")
            val player = ExoPlayer.Builder(this@VideoLiveWallpaperService).build()
            mediaPlayer = player
            player.repeatMode = Player.REPEAT_MODE_ALL
            val playbackParams = PlaybackParameters(0.85f)
            mediaPlayer.playbackParameters = playbackParams
            mediaPlayer.volume = 0f
            url = PreferenceManager.getDefaultSharedPreferences(this@VideoLiveWallpaperService).getString("wp_url", "").orEmpty()

//            senSensorManager = this@VideoLiveWallpaperService.getSystemService(Context.SENSOR_SERVICE) as SensorManager
//            senAccelerometer = senSensorManager?.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
//            senSensorManager?.registerListener(
//                this,
//                senAccelerometer,
//                SensorManager.SENSOR_DELAY_FASTEST
//            )
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            Log.i(TAG, "onSurfaceCreated")
            mediaPlayer.setVideoSurface(holder.surface)
            val mediaItem: MediaItem = MediaItem.fromUri(url)
            // Set the media item to be played.
            mediaPlayer.setMediaItem(mediaItem)
            // Prepare the player.
            mediaPlayer.prepare()
            mediaPlayer.play()
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            Log.i(TAG, "( INativeWallpaperEngine ): onSurfaceDestroyed")
            playheadTime = mediaPlayer.currentPosition.toInt()
            mediaPlayer.stop()
            mediaPlayer.release()
        }

        private var lastUpdateAt = 0L
        private var minTime = 300

        override fun onSensorChanged(event: SensorEvent) {
            val mySensor: Sensor = event.sensor
            if (mySensor.type == Sensor.TYPE_ROTATION_VECTOR) {

                // Convert the rotation-vector to a 4x4 matrix.
                try {
                    SensorManager.getRotationMatrixFromVector(rotMat, event.values)
                } catch (e: IllegalArgumentException) {
                    if (event.values.size > 3) {
                        // Note 3 bug
                        val newVector = floatArrayOf(
                            event.values.get(0),
                            event.values.get(1),
                            event.values.get(2)
                        )
                        SensorManager.getRotationMatrixFromVector(rotMat, newVector)
                    }
                }
                SensorManager.remapCoordinateSystem(
                    rotMat,
                    SensorManager.AXIS_Y, SensorManager.AXIS_X,
                    rotMat
                )
                SensorManager.getOrientation(rotMat, vals)
                vals[0] = Math.toDegrees(vals[0].toDouble()).toFloat()
                vals[1]= Math.toDegrees(vals[1].toDouble()).toFloat()
                vals[2] = Math.toDegrees(vals[2].toDouble()).toFloat()
                val leftfloat =
                    (this.sideHorizontalMargin - vals.get(1) * this.horizontalMultiplier)
                val topfloat: Int
                topfloat = if (vals.get(2) > 0) {
                    (this.sideVerticalMargin + vals.get(2) * this.verticalMultiplier).toInt()
                } else {
                    (this.sideVerticalMargin - vals.get(2) * this.verticalMultiplier).toInt()
                }

                if (System.currentTimeMillis() - lastUpdateAt < minTime) {
                    return
                }
                var factor = leftfloat
                if (factor < 0) {
                    factor = -1 * factor
                }
                var result =  (0..100).convert(factor.toInt(), 0..4)

                var newSpeed = result.toFloat()

                if (result == 0) {
                    newSpeed = 0.85f
                }
                Log.d("lol", "values changed: " + newSpeed)
                mediaPlayer.setPlaybackSpeed(newSpeed)

                lastUpdateAt = System.currentTimeMillis()

            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        fun onPause() {
            senSensorManager?.unregisterListener(this)
        }

        override fun onDestroy() {
            try {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                senSensorManager?.unregisterListener(this)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            super.onDestroy()
        }

        fun onResume() {
            senSensorManager?.registerListener(
                this,
                senAccelerometer,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }


    companion object {
        protected var playheadTime = 0
    }

    fun IntRange.convert(number: Int, target: IntRange): Int {
        val ratio = number.toFloat() / (endInclusive - start)
        return (ratio * (target.endInclusive - target.start)).toInt()
    }


}