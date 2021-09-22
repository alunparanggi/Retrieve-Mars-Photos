package com.tonicapp.marsphotos.ui.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonicapp.marsphotos.network.MarsApi
import com.tonicapp.marsphotos.network.MarsProperty
import kotlinx.coroutines.launch
import java.lang.Exception

class OverviewViewModel: ViewModel() {

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    init {
        getMarsPhoto()
    }

    private fun getMarsPhoto(){
        viewModelScope.launch {
            try {
                _properties.value = MarsApi.retrofitService.getPhotos()
            } catch (e: Exception) {
                _properties.value = ArrayList()
            }
        }
    }

}