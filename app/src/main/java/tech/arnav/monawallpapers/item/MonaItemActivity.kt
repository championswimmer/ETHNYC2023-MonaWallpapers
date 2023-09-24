package tech.arnav.monawallpapers.item

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.serialization.json.Json
import tech.arnav.monawallpapers.VideoLiveWallpaperService
import tech.arnav.monawallpapers.databinding.ActivityMonaItemBinding
import tech.arnav.monawallpapers.http.MonaDatum


class MonaItemActivity : AppCompatActivity() {
    val TAG = "MonaItemActivity"
    lateinit var _binding: ActivityMonaItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMonaItemBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val data = intent.getStringExtra("data")
        val monaData = Json.decodeFromString<MonaDatum>(data!!)

        _binding.toolbar.title = monaData.title
        _binding.toolbar.subtitle = monaData.artist
//        _binding.tvMonaArtist.text = monaData.artist
//        _binding.tvMonaTitle.text = monaData.title

        // Load webm with exoplayer
        val player = ExoPlayer.Builder(this).build()
        _binding.playerView.player = player

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

//        var width = displayMetrics.widthPixels
//        var height = displayMetrics.heightPixels
//
//        val screenWidth = width
//        _binding.playerView.layoutParams.height =
//            (height.toFloat() / width.toFloat() *
//                    screenWidth.toFloat()).toInt()
//        _binding.playerView.setLayoutParams(_binding.playerView.layoutParams)

        // Build the media item.
        val mediaItem: MediaItem = MediaItem.fromUri(monaData.webmURL)
        // Set the media item to be played.
        player.setMediaItem(mediaItem)
        // Prepare the player.
        player.prepare()
        player.repeatMode = ExoPlayer.REPEAT_MODE_ALL
        player.volume = 0f

        // Start the playback.
        player.play()

        _binding.btnSetWallpaper.setOnClickListener {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString("wp_url", monaData.webmURL).apply()
            val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
            intent.putExtra(
                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, ComponentName(
                    this,
                    VideoLiveWallpaperService::class.java
                )
            )
            startActivity(intent)
        }


    }
}