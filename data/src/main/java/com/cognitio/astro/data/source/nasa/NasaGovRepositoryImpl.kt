package com.cognitio.astro.data.source.nasa

import android.net.Uri
import android.util.Log
import com.cognitio.astro.data.BuildConfig
import com.cognitio.astro.data.common.UrlConstants
import com.cognitio.astro.data.source.nasa.network.api.NasaGovApi
import com.cognitio.astro.data.source.nasa.network.dto.PictureOfTheDayDTO
import com.cognitio.astro.domain.repository.NasaGovRepository
import javax.inject.Inject

class NasaGovRepositoryImpl @Inject constructor(
    private val nasaGovApi: NasaGovApi
) : NasaGovRepository {
    override suspend fun getPicturesOfTheDay(): List<PictureOfTheDayDTO> {
        val picturesOfTheDayUri = Uri.Builder()
            .scheme(UrlConstants.HTTPS_PROTOCOL)
            .encodedAuthority(UrlConstants.BASE_NASA_GOV_URL)
            .appendEncodedPath(UrlConstants.APOD_PATH)
            .appendQueryParameter(UrlConstants.START_DATE_PARAM, "2023-03-31")
            .appendQueryParameter(UrlConstants.END_DATE_PARAM, "2023-06-01")
            .appendQueryParameter(UrlConstants.API_KEY_PARAM, BuildConfig.API_KEY)
            .build()
        return nasaGovApi.getPictureOfTheDay(picturesOfTheDayUri.toString())
    }
}