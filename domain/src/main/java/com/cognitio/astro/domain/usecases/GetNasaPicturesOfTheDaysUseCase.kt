package com.cognitio.astro.domain.usecases

import com.cognitio.astro.domain.model.NasaPictureOfTheDay
import com.cognitio.astro.domain.repository.nasa.NasaGovRepository
import com.cognitio.astro.domain.repository.nasa.dto.BaseNasaPictureOfTheDayDTO
import com.cognitio.astro.domain.repository.nasa.dto.toPictureOfTheDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class GetNasaPicturesOfTheDaysUseCase @Inject constructor(
    private val nasaGovRepository: NasaGovRepository
) {
    operator fun invoke(startDate: Long, endDate: Long): Flow<List<NasaPictureOfTheDay>> = flow {
        try {
            val nasaPictureOfTheDays: List<NasaPictureOfTheDay> = nasaGovRepository.getPicturesOfTheDay(startDate, endDate)
                .map(BaseNasaPictureOfTheDayDTO::toPictureOfTheDay)
            emit(nasaPictureOfTheDays)
        } catch(e: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)
}