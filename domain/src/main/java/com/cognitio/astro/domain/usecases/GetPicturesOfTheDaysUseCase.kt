package com.cognitio.astro.domain.usecases

import com.cognitio.astro.domain.model.PictureOfTheDay
import com.cognitio.astro.domain.repository.NasaGovRepository
import com.cognitio.astro.domain.repository.dto.BasePictureOfTheDayDTO
import com.cognitio.astro.domain.repository.dto.toPictureOfTheDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class GetPicturesOfTheDaysUseCase @Inject constructor(
    private val nasaGovRepository: NasaGovRepository
) {
    operator fun invoke(startDate: Long, endDate: Long): Flow<List<PictureOfTheDay>> = flow {
        try {
            val pictureOfTheDays: List<PictureOfTheDay> = nasaGovRepository.getPicturesOfTheDay(startDate, endDate)
                .map(BasePictureOfTheDayDTO::toPictureOfTheDay)
            emit(pictureOfTheDays)
        } catch(e: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)
}