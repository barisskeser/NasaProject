package com.baris.nasaproject.data.remote.nasa.dto

import com.google.gson.annotations.SerializedName

data class PhotoManifest(
    @SerializedName("landing_date") val landingDate: String,
    @SerializedName("launch_date") val launchDate: String,
    @SerializedName("max_date") val maxDate: String,
    @SerializedName("max_sol") val maxSol: Int,
    val name: String,
    val photos: List<PhotoX>,
    val status: String,
    @SerializedName("total_photos") val totalPhotos: Int
)