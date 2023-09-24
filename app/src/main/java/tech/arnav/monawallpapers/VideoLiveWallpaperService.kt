package tech.arnav.monawallpapers

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

    internal inner class VideoEngine : WallpaperService.Engine() {

        private val TAG = javaClass.simpleName
        private val mediaPlayer: ExoPlayer
        private val url: String

        init {
            Log.i(TAG, "( VideoEngine )")
            val player = ExoPlayer.Builder(this@VideoLiveWallpaperService).build()
            mediaPlayer = player
            player.repeatMode = Player.REPEAT_MODE_ALL
            val playbackParams = PlaybackParameters(0.6f)
            mediaPlayer.playbackParameters = playbackParams
            mediaPlayer.volume = 0f
            url = PreferenceManager.getDefaultSharedPreferences(this@VideoLiveWallpaperService).getString("wp_url", "").orEmpty()
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
    }

    companion object {
        protected var playheadTime = 0
    }

}