package com.baris.nasaproject.data.remote.nasa.dto

import com.baris.nasaproject.domain.model.Nasa

data class NasaDto(
    val photos: List<Photo>
)

fun NasaDto.toNasa(): Nasa = Nasa(photos = this.photos)
