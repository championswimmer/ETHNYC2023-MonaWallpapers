package tech.arnav.monawallpapers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.arnav.monawallpapers.http.MonaData
import tech.arnav.monawallpapers.http.MonaRepository

class MainViewModel : ViewModel() {

    val monoRepo = MonaRepository()

    private val _monaDataLiveData: MutableLiveData<MonaData> = MutableLiveData()
    val monaDataLiveData: LiveData<MonaData> = _monaDataLiveData

    fun getMonaData() {
        viewModelScope.launch {
            val monaData = monoRepo.getMonaData()
            _monaDataLiveData.postValue(monaData)
        }
    }
}