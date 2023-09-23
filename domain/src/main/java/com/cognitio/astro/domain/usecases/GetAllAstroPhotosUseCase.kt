package com.cognitio.astro.domain.usecases

import com.cognitio.astro.domain.repository.firebase.CloudStorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllAstroPhotosUseCase @Inject constructor(
    private val cloudStorageRepository: CloudStorageRepository
) {
    operator fun invoke() = flow {
        try {
            emit(cloudStorageRepository.getAllMoonFiles())
        } catch (ignore: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)
}