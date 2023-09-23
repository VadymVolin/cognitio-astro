package com.cognitio.astro.domain.repository.firebase

interface CloudStorageRepository {
    suspend fun getAllMoonFiles(): List<String>
}