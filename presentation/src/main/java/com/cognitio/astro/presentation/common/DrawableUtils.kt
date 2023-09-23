package com.cognitio.astro.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.cognitio.astro.presentation.R

object DrawableUtils {

    @Composable
    fun getPainterIcon(iconResource: IconResource) = painterResource(iconResource.resourceId)

    enum class IconResource(val resourceId: Int) {
        Camera(R.drawable.photo_camera),
        Gallery(R.drawable.gallery_thumbnail),
        PlanetPlaceholder(R.drawable.planet_placeholder)
    }

}