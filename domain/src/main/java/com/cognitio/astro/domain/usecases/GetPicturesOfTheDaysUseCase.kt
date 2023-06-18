package com.cognitio.astro.domain.usecases

import com.cognitio.astro.domain.model.PictureOfTheDay
import com.cognitio.astro.domain.repository.NasaGovRepository
import com.cognitio.astro.domain.repository.dto.BasePictureOfTheDayDTO
import com.cognitio.astro.domain.repository.dto.toPictureOfTheDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetPicturesOfTheDaysUseCase @Inject constructor(
    private val nasaGovRepository: NasaGovRepository
) {
    operator fun invoke(): Flow<List<PictureOfTheDay>> = flow {
        try {
            val pictureOfTheDays: List<PictureOfTheDay> = nasaGovRepository.getPicturesOfTheDay()
                .map(BasePictureOfTheDayDTO::toPictureOfTheDay)
            emit(pictureOfTheDays)
        } catch(e: Exception) {
            emit(emptyList())
        }
    }
}