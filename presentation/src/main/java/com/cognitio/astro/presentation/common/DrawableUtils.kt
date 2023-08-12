package com.cognitio.astro.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.cognitio.astro.presentation.R

object DrawableUtils {

    @Composable
    fun getImagePlaceholder() = painterResource(R.drawable.planet_placeholder)

    fun getImagePlaceholderId() = R.drawable.planet_placeholder

}