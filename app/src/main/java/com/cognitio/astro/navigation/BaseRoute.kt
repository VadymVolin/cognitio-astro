package com.cognitio.astro.navigation

import androidx.annotation.StringRes
import com.cognitio.astro.R

sealed class BaseRoute(val route: String, @StringRes val stringResourceId: Int) {
    object HomeRoute : BaseRoute("home", R.string.home_route)
    object AddRoute : BaseRoute("add", R.string.add_route)
    object SettingsRoute : BaseRoute("settings", R.string.settings_route)
}
