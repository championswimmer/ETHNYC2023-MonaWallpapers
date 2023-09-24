package tech.arnav.monawallpapers.item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import tech.arnav.monawallpapers.R
import tech.arnav.monawallpapers.databinding.ActivityMonaItemBinding
import tech.arnav.monawallpapers.http.MonaDatum

class MonaItemActivity : AppCompatActivity() {
    val TAG = "MonaItemActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(ActivityMonaItemBinding.inflate(layoutInflater)) {
            setContentView(root)
        }

        val data = intent.getStringExtra("data")
        val monaData = Json.decodeFromString<MonaDatum>(data!!)

    }
}