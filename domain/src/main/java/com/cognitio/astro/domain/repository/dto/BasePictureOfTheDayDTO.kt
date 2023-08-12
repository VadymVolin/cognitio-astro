package com.cognitio.astro.domain.repository.dto

import com.cognitio.astro.domain.model.PictureOfTheDay
import com.cognitio.astro.util.StringUtils
import java.util.Locale

interface BasePictureOfTheDayDTO {
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

fun BasePictureOfTheDayDTO.toPictureOfTheDay(): PictureOfTheDay {
    val mediaType = PictureOfTheDay.MediaType.valueOf(this.mediaType.uppercase(Locale.getDefault()))
    return PictureOfTheDay(
        title = this.title,
        description = this.explanation,
        author = this.copyright,
        date = this.date,
        mediaType = mediaType,
        videoUrl = if (mediaType == PictureOfTheDay.MediaType.VIDEO) this.thumbnailUrl else this.hdurl,
        imageUrl = if (mediaType == PictureOfTheDay.MediaType.IMAGE) this.url else this.hdurl
    )
}