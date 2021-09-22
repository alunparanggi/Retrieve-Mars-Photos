package com.tonicapp.marsphotos.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonicapp.marsphotos.network.MarsApi
import com.tonicapp.marsphotos.network.MarsProperty
import kotlinx.coroutines.launch
import java.lang.Exception

enum class NetworkStatus { LOADING, DONE, ERROR }

class OverviewViewModel: ViewModel() {

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    private val _status = MutableLiveData<NetworkStatus>()
    val status: LiveData<NetworkStatus>
        get() = _status

    init {
        getMarsPhoto()
    }

    private fun getMarsPhoto(){
        viewModelScope.launch {
            _status.value = NetworkStatus.LOADING
            try {
                _properties.value = MarsApi.retrofitService.getPhotos()
                _status.value = NetworkStatus.DONE
            } catch (e: Exception) {
                _properties.value = ArrayList()
                _status.value = NetworkStatus.ERROR
            }
        }
    }

}