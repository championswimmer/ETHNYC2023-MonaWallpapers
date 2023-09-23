package tech.arnav.monawallpapers

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import tech.arnav.monawallpapers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    lateinit var viewModel : MainViewModel
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        Log.d(TAG, "onCreate: ")

        _binding.btnFetchMonoData.setOnClickListener {
            Log.d(TAG, "setOnClickListener: ")
            viewModel.getMonaData()
        }

    }
}