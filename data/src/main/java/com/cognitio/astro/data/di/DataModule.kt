package com.cognitio.astro.data.di

import com.cognitio.astro.data.source.nasa.NasaGovRepositoryImpl
import com.cognitio.astro.data.source.nasa.network.api.NasaGovApi
import com.cognitio.astro.domain.repository.NasaGovRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    companion object {
        @Singleton
        @Provides
        fun provideMoshi(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

        @Singleton
        @Provides
        fun provideRetrofit(moshi: Moshi): Retrofit =
            Retrofit.Builder().baseUrl("https://temp.com/").addConverterFactory(MoshiConverterFactory.create(moshi)).build()

        @Singleton
        @Provides
        fun provideNasaGovApi(retrofit: Retrofit): NasaGovApi = retrofit.create(NasaGovApi::class.java)
    }

    @Singleton
    @Binds
    abstract fun bindNasaGovRepository(nasaGovRepository: NasaGovRepositoryImpl): NasaGovRepository
}