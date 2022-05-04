package com.baris.nasaproject.domain.usecase

import com.baris.nasaproject.common.Resource
import com.baris.nasaproject.domain.repository.NasaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCamerasUseCase @Inject constructor(
    private val repository: NasaRepository
) {

    operator fun invoke(roverName: String, sol: Int): Flow<Resource<List<String>>> = flow {
        try {
            emit(Resource.Loading<List<String>>())
            val cameras = repository.getCameras(roverName, sol)
            emit(Resource.Success<List<String>>(cameras))
        } catch (e: HttpException){
            emit(Resource.Error<List<String>>(message = e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException){
            emit(Resource.Error<List<String>>(message = "Couldn't reach server. Check your internet connection"))
        }
    }

}