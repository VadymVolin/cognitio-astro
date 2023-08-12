package com.cognitio.astro.presentation.navigation

import androidx.annotation.StringRes
import com.cognitio.astro.presentation.R

sealed class BaseRoute(val route: String, @StringRes val stringResourceId: Int) {
    object HomeRoute : BaseRoute("home", R.string.home_route)
    object GalleryRoute : BaseRoute("gallery", R.string.gallery_route)

    object AddRoute : BaseRoute("add", R.string.add_route)
    object SettingsRoute : BaseRoute("settings", R.string.settings_route)
}
