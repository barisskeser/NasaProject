package com.baris.nasaproject.domain.model

import com.baris.nasaproject.data.remote.nasa.dto.Photo

data class Nasa(
    val photos: List<Photo>
)
