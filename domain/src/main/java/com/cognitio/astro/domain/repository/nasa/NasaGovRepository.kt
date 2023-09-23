package com.cognitio.astro.domain.repository.nasa

import com.cognitio.astro.domain.repository.nasa.dto.BaseNasaPictureOfTheDayDTO


interface NasaGovRepository {
    suspend fun getPicturesOfTheDay(
        startDate: Long,
        endDate: Long
    ): List<BaseNasaPictureOfTheDayDTO>
}