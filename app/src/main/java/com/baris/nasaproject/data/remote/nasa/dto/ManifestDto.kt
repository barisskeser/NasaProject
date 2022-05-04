package com.baris.nasaproject.data.remote.nasa.dto

import com.google.gson.annotations.SerializedName

data class ManifestDto(
    @SerializedName("photo_manifest")
    val photoManifest: PhotoManifest
)