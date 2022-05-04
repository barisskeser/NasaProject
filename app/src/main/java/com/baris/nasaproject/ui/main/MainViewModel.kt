package com.baris.nasaproject.ui.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baris.nasaproject.common.Constants
import com.baris.nasaproject.common.Resource
import com.baris.nasaproject.domain.model.Nasa
import com.baris.nasaproject.domain.model.State
import com.baris.nasaproject.domain.usecase.GetCamerasUseCase
import com.baris.nasaproject.domain.usecase.GetRoverPhotosUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRoverPhotosUseCase: GetRoverPhotosUseCase,
    private val getCamerasUseCase: GetCamerasUseCase
) : ViewModel() {

    private val _nasaStateLiveData = MutableLiveData<State<Nasa>>()
    val nasaStateLiveData: LiveData<State<Nasa>> = _nasaStateLiveData

    private val _cameraStateLiveData = MutableLiveData<State<List<String>>>()
    val cameraStateLiveData: LiveData<State<List<String>>> = _cameraStateLiveData

    private var page = 1
    private var isDone: Boolean = false

    var camera = Constants.CAMERA_ALL

    fun resetPage() {
        page = 1
        isDone = false
    }

    fun gotAllPage() {
        isDone = true
    }

    fun getRoverPhotos(roverName: String, sol: Int) {
        if (!isDone)
            getRoverPhotosUseCase.invoke(roverName, camera, sol, page++).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _nasaStateLiveData.value = State(loading = true)
                    }
                    is Resource.Success -> {
                        _nasaStateLiveData.value = State(data = result.data)
                    }
                    is Resource.Error -> {
                        _nasaStateLiveData.value = State(errorMessage = result.message ?: "")
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun getRoverCameras(roverName: String, sol: Int){
        getCamerasUseCase.invoke(roverName, sol).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _cameraStateLiveData.value = State(loading = true)
                }
                is Resource.Success -> {
                    _cameraStateLiveData.value = State(data = result.data)
                }
                is Resource.Error -> {
                    _cameraStateLiveData.value = State(errorMessage = result.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }
}