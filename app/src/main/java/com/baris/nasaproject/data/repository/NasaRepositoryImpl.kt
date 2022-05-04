package com.baris.nasaproject.data.repository

import android.util.Log
import com.baris.nasaproject.common.Constants
import com.baris.nasaproject.data.remote.nasa.NasaApi
import com.baris.nasaproject.data.remote.nasa.dto.NasaDto
import com.baris.nasaproject.domain.repository.NasaRepository
import javax.inject.Inject

class NasaRepositoryImpl @Inject constructor(
    private val api: NasaApi
): NasaRepository {

    private val TAG = "NasaRepository"
    override suspend fun getRoverPhotos(roverName: String, camera: String, sol: Int, page: Int): NasaDto {
        Log.d(TAG, "request for $camera of camera of $roverName Rover on page $page at sol $sol")
        return api.getRoverPhotos(
            roverName = roverName,
            camera = if(camera == Constants.CAMERA_ALL) null else camera,
            sol = sol,
            page = page
        )
    }

    override suspend fun getCameras(roverName: String, sol: Int): List<String> {

        api.getManifests(roverName).photoManifest.photos.onEach {
            if(it.sol == sol){
                return it.cameras
            }
        }

        return emptyList()
    }

}