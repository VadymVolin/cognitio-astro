package com.cognitio.astro.domain.repository.nasa.dto

import com.cognitio.astro.domain.model.NasaPictureOfTheDay
import com.cognitio.astro.util.StringUtils
import java.util.Locale

interface BaseNasaPictureOfTheDayDTO {
    val copyright: String
        get() = StringUtils.EMPTY_STRING
    val date: String
        get() = StringUtils.EMPTY_STRING
    val explanation: String
        get() = StringUtils.EMPTY_STRING
    val hdurl: String
        get() = StringUtils.EMPTY_STRING
    val thumbnailUrl: String
        get() = StringUtils.EMPTY_STRING
    val mediaType: String
        get() = StringUtils.EMPTY_STRING
    val serviceVersion: String
        get() = StringUtils.EMPTY_STRING
    val title: String
        get() = StringUtils.EMPTY_STRING
    val url: String
        get() = StringUtils.EMPTY_STRING
}

fun BaseNasaPictureOfTheDayDTO.toPictureOfTheDay(): NasaPictureOfTheDay {
    val mediaType = NasaPictureOfTheDay.MediaType.valueOf(this.mediaType.uppercase(Locale.getDefault()))
    return NasaPictureOfTheDay(
        title = this.title,
        description = this.explanation,
        author = this.copyright,
        date = this.date,
        mediaType = mediaType,
        videoUrl = if (mediaType == NasaPictureOfTheDay.MediaType.VIDEO) this.thumbnailUrl else this.hdurl,
        imageUrl = if (mediaType == NasaPictureOfTheDay.MediaType.IMAGE) this.url else this.hdurl
    )
}