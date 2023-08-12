package com.cognitio.astro.data.source.nasa

import android.net.Uri
import com.cognitio.astro.data.BuildConfig
import com.cognitio.astro.data.common.UrlConstants
import com.cognitio.astro.data.source.nasa.network.api.NasaGovApi
import com.cognitio.astro.data.source.nasa.network.dto.PictureOfTheDayDTO
import com.cognitio.astro.domain.repository.NasaGovRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

class NasaGovRepositoryImpl @Inject constructor(
    private val nasaGovApi: NasaGovApi
) : NasaGovRepository {
    override suspend fun getPicturesOfTheDay(): List<PictureOfTheDayDTO> {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val picturesOfTheDayUri = Uri.Builder()
            .scheme(UrlConstants.HTTPS_PROTOCOL)
            .encodedAuthority(UrlConstants.BASE_NASA_GOV_URL)
            .appendEncodedPath(UrlConstants.APOD_PATH)
            .appendQueryParameter(UrlConstants.START_DATE_PARAM, sdf.format(Date(System.currentTimeMillis() - 30L.days.inWholeMilliseconds)))
            .appendQueryParameter(UrlConstants.END_DATE_PARAM, sdf.format(Date(System.currentTimeMillis() - 1L.days.inWholeMilliseconds)))
            .appendQueryParameter(UrlConstants.THUMBS_PARAM, true.toString())
            .appendQueryParameter(UrlConstants.API_KEY_PARAM, BuildConfig.API_KEY)
            .build()
        return nasaGovApi.getPictureOfTheDay(picturesOfTheDayUri.toString())
    }
}