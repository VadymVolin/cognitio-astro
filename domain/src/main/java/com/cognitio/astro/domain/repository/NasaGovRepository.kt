package com.cognitio.astro.domain.repository

import com.cognitio.astro.domain.repository.dto.BasePictureOfTheDayDTO

interface NasaGovRepository {
    suspend fun getPicturesOfTheDay(): List<BasePictureOfTheDayDTO>
}