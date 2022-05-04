package com.baris.nasaproject.domain.repository

import com.baris.nasaproject.data.remote.nasa.dto.Camera
import com.baris.nasaproject.data.remote.nasa.dto.NasaDto

interface NasaRepository {

    suspend fun getRoverPhotos(roverName: String, camera: String, sol: Int, page: Int): NasaDto

    suspend fun getCameras(roverName: String, sol: Int): List<String>

}