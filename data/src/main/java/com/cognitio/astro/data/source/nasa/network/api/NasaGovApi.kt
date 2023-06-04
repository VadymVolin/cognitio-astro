package com.cognitio.astro.data.source.nasa.network.api

import com.cognitio.astro.data.source.nasa.network.dto.PictureOfTheDayDTO
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * API interface for the [https://api.nasa.gov/](https://api.nasa.gov/)
 */
interface NasaGovApi {
    @GET
    suspend fun getPictureOfTheDay(@Url url: String): List<PictureOfTheDayDTO>
}