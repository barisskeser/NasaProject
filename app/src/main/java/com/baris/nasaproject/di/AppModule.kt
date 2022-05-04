package com.baris.nasaproject.di

import com.baris.nasaproject.common.Constants
import com.baris.nasaproject.data.remote.nasa.NasaApi
import com.baris.nasaproject.data.repository.NasaRepositoryImpl
import com.baris.nasaproject.domain.repository.NasaRepository
import com.baris.nasaproject.domain.usecase.GetCamerasUseCase
import com.baris.nasaproject.domain.usecase.GetRoverPhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNasaApi(): NasaApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApi::class.java)

    @Provides
    fun provideNasaRepository(api: NasaApi): NasaRepository = NasaRepositoryImpl(api)

    @Provides
    fun provideGetRoverPhotosUseCase(repository: NasaRepository): GetRoverPhotosUseCase = GetRoverPhotosUseCase(repository)

    @Provides
    fun provideGetCamerasUseCase(repository: NasaRepository): GetCamerasUseCase = GetCamerasUseCase(repository)
}