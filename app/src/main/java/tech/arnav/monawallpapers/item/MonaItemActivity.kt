package tech.arnav.monawallpapers.item

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.serialization.json.Json
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

        _binding.tvMonaArtist.text = monaData.artist
        _binding.tvMonaTitle.text = monaData.title

        // Load webm with exoplayer
        val player = ExoPlayer.Builder(this).build()
        _binding.playerView.player = player

        // Build the media item.
        val mediaItem: MediaItem = MediaItem.fromUri(monaData.webmURL)
        // Set the media item to be played.
        player.setMediaItem(mediaItem)
        // Prepare the player.
        player.prepare()
        player.repeatMode = ExoPlayer.REPEAT_MODE_ALL
        // Start the playback.
        player.play()
    }
}