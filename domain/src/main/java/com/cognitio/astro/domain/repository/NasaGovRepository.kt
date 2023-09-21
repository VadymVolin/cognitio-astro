package com.cognitio.astro.domain.repository

import com.cognitio.astro.domain.repository.dto.BasePictureOfTheDayDTO

interface NasaGovRepository {
    suspend fun getPicturesOfTheDay(
        startDate: Long,
        endDate: Long
    ): List<BasePictureOfTheDayDTO>
}