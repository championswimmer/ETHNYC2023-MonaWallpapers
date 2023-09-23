package tech.arnav.monawallpapers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.arnav.monawallpapers.http.MonaRepository

class MainViewModel : ViewModel() {

    val monoRepo = MonaRepository()

    fun getMonaData() {
        viewModelScope.launch {
            monoRepo.getMonaData()
        }
    }
}