package com.baris.nasaproject.domain.usecase

import com.baris.nasaproject.common.Resource
import com.baris.nasaproject.data.remote.nasa.dto.toNasa
import com.baris.nasaproject.domain.model.Nasa
import com.baris.nasaproject.domain.repository.NasaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRoverPhotosUseCase @Inject constructor(
    private val repository: NasaRepository
) {
    operator fun invoke(roverName: String, camera: String, sol: Int, page: Int): Flow<Resource<Nasa>> = flow {
        try {
            emit(Resource.Loading<Nasa>())
            val nasa = repository.getRoverPhotos(roverName, camera, sol, page).toNasa()
            emit(Resource.Success<Nasa>(nasa))
        } catch (e: HttpException){
            emit(Resource.Error<Nasa>(message = e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException){
            emit(Resource.Error<Nasa>(message = "Couldn't reach server. Check your internet connection"))
        }
    }
}