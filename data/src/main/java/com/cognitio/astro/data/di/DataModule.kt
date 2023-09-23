package com.cognitio.astro.data.di

import com.cognitio.astro.data.source.firebase.CloudStorageRepositoryImpl
import com.cognitio.astro.util.UrlConstants
import com.cognitio.astro.data.source.nasa.NasaGovRepositoryImpl
import com.cognitio.astro.data.source.nasa.network.api.NasaGovApi
import com.cognitio.astro.domain.repository.firebase.CloudStorageRepository
import com.cognitio.astro.domain.repository.nasa.NasaGovRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
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
            Retrofit.Builder().baseUrl(UrlConstants.MOCK_URL)
                .client(
                    OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .callTimeout(60, TimeUnit.SECONDS)
                        .build()
                )
                .addConverterFactory(MoshiConverterFactory.create(moshi)).build()

        @Singleton
        @Provides
        fun provideNasaGovApi(retrofit: Retrofit): NasaGovApi = retrofit.create(NasaGovApi::class.java)
    }

    @Singleton
    @Binds
    abstract fun bindNasaGovRepository(nasaGovRepository: NasaGovRepositoryImpl): NasaGovRepository

    @Singleton
    @Binds
    abstract fun bindCloudStorageRepository(cloudStorageRepository: CloudStorageRepositoryImpl): CloudStorageRepository
}