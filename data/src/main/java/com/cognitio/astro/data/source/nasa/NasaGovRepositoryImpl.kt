package com.cognitio.astro.data.source.nasa

import com.cognitio.astro.data.BuildConfig
import com.cognitio.astro.data.source.nasa.network.api.NasaGovApi
import com.cognitio.astro.data.source.nasa.network.dto.NasaPictureOfTheDayDTO
import com.cognitio.astro.domain.repository.nasa.NasaGovRepository
import com.cognitio.astro.util.DateConstants
import com.cognitio.astro.util.UrlConstants
import okhttp3.HttpUrl
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class NasaGovRepositoryImpl @Inject constructor(
    private val nasaGovApi: NasaGovApi
) : NasaGovRepository {
    override suspend fun getPicturesOfTheDay(startDate: Long, endDate: Long): List<NasaPictureOfTheDayDTO> {
        val sdf = SimpleDateFormat(DateConstants.DATE_FORMAT, Locale.getDefault())
        val picturesOfTheDayUri = HttpUrl.Builder().scheme(UrlConstants.HTTPS_PROTOCOL)
            .host(UrlConstants.BASE_NASA_GOV_URL)
            .addPathSegment(UrlConstants.APOD_PATH)
            .addQueryParameter(UrlConstants.START_DATE_PARAM, sdf.format(Date(startDate)))
            .addQueryParameter(UrlConstants.END_DATE_PARAM, sdf.format(Date(endDate)))
            .addQueryParameter(UrlConstants.THUMBS_PARAM, true.toString())
            .addQueryParameter(UrlConstants.API_KEY_PARAM, BuildConfig.API_KEY)
            .build()
        return nasaGovApi.getPictureOfTheDay(picturesOfTheDayUri.toString())
    }
}