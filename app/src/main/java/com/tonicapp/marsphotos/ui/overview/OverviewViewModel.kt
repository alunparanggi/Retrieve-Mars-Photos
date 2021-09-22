package com.tonicapp.marsphotos.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonicapp.marsphotos.network.MarsApi
import com.tonicapp.marsphotos.network.MarsApiFilter
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

    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty

    init {
        getMarsPhoto(MarsApiFilter.SHOW_ALL)
    }

    private fun getMarsPhoto(filter: MarsApiFilter){
        viewModelScope.launch {
            _status.value = NetworkStatus.LOADING
            try {
                _properties.value = MarsApi.retrofitService.getPhotos(filter.value)
                _status.value = NetworkStatus.DONE
            } catch (e: Exception) {
                _properties.value = ArrayList()
                _status.value = NetworkStatus.ERROR
            }
        }
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param marsProperty The [MarsProperty] that was clicked on.
     */
    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }


    fun updateFilter(filter: MarsApiFilter){
        getMarsPhoto(filter)
    }

}