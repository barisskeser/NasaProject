package com.baris.nasaproject.data.remote.nasa

import com.baris.nasaproject.BuildConfig
import com.baris.nasaproject.data.remote.nasa.dto.ManifestDto
import com.baris.nasaproject.data.remote.nasa.dto.NasaDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaApi {

    @GET("mars-photos/api/v1/rovers/{rover}/photos")
    suspend fun getRoverPhotos(
        @Path("rover") roverName: String,
        @Query("camera") camera: String?,
        @Query("sol") sol: Int,
        @Query("page") page: Int,
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): NasaDto


    @GET("https://api.nasa.gov/mars-photos/api/v1/manifests/{rover}")
    suspend fun getManifests(
        @Path("rover") roverName: String,
        @Query("api_key") key: String = BuildConfig.API_KEY
    ): ManifestDto
}