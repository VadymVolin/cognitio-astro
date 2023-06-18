package com.cognitio.astro.domain.model

import com.cognitio.astro.util.StringUtils

class PictureOfTheDay(
    val title: String = StringUtils.EMPTY_STRING,
    val description: String = StringUtils.EMPTY_STRING,
    val author: String = StringUtils.EMPTY_STRING,
    val date: String = StringUtils.EMPTY_STRING,
    val imageUrl: String = StringUtils.EMPTY_STRING,
    val videoUrl: String = StringUtils.EMPTY_STRING,
    val mediaType: MediaType = MediaType.UNDEFINED
) {
    enum class MediaType {
        VIDEO, IMAGE, UNDEFINED
    }

    override fun toString(): String {
        return "PictureOfTheDay(title='$title', description='$description', author='$author', date='$date', imageUrl='$imageUrl', videoUrl='$videoUrl', mediaType=$mediaType)"
    }
}