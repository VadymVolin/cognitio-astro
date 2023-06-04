package com.cognitio.astro.data.source.nasa.network.dto

import android.os.Parcelable
import com.cognitio.astro.domain.repository.dto.BasePictureOfTheDayDTO
import com.cognitio.astro.util.StringUtils
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
class PictureOfTheDayDTO(
    @Json(name = "copyright")
    override val copyright: String = StringUtils.EMPTY_STRING,
    @Json(name = "date")
    override val date: String = StringUtils.EMPTY_STRING,
    @Json(name = "explanation")
    override val explanation: String = StringUtils.EMPTY_STRING,
    /**
     * should be used if [mediaType] = "image"
     * else use [thumbnailUrl] if available
     */
    @Json(name = "hdurl")
    override val hdurl: String = StringUtils.EMPTY_STRING,
    /**
     * should be used if [mediaType] = "video"
     * else use [hdurl] if available
     */
    @Json(name = "thumbnail_url")
    override val thumbnailUrl: String = StringUtils.EMPTY_STRING,
    @Json(name = "media_type")
    override val mediaType: String = StringUtils.EMPTY_STRING,
    @Json(name = "service_version")
    override val serviceVersion: String = StringUtils.EMPTY_STRING,
    @Json(name = "title")
    override val title: String = StringUtils.EMPTY_STRING,
    /**
     * contains video url if [mediaType] = "video"
     * else image url
     */
    @Json(name = "url")
    override val url: String = StringUtils.EMPTY_STRING
) : BasePictureOfTheDayDTO, Parcelable
