package com.baris.nasaproject.data.remote.nasa.dto

import com.google.gson.annotations.SerializedName

data class PhotoX(
    val cameras: List<String>,
    @SerializedName("earth_date") val earthDate: String,
    val sol: Int,
    @SerializedName("total_photos") val totalPhotos: Int
)