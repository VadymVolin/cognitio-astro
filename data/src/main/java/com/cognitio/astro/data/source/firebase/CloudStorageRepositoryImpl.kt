package com.cognitio.astro.data.source.firebase

import com.cognitio.astro.domain.repository.firebase.CloudStorageRepository
import com.cognitio.astro.util.UrlConstants
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CloudStorageRepositoryImpl @Inject constructor() : CloudStorageRepository {

    private val _storage: FirebaseStorage
        get() = Firebase.storage

    override suspend fun getAllMoonFiles(): List<String> {
        val uris = mutableListOf<String>()
        _storage.reference
            .root
            .child(UrlConstants.ASTRO_PHOTO_STORAGE_REF)
            .child(UrlConstants.MOON_STORAGE_REF)
            .listAll()
            .await()
            .items
            .forEach { imageUriRef ->
                val uri = imageUriRef.downloadUrl.await()
                uris.add(uri.toString())
            }
        return uris
    }

}