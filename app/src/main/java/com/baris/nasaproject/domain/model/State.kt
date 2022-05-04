package com.baris.nasaproject.domain.model

data class State<T> (
    val data: T? = null,
    val loading: Boolean = false,
    val errorMessage: String = ""
)
