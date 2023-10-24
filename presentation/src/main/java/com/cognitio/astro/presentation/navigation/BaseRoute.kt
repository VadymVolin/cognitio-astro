package com.cognitio.astro.presentation.navigation

import androidx.annotation.StringRes
import com.cognitio.astro.presentation.R

sealed class BaseRoute(val route: String, @StringRes val stringResourceId: Int) {
    data object PODRoute : BaseRoute("pod", R.string.pod_route)
    data object GalleryRoute : BaseRoute("gallery", R.string.gallery_route)
    data object CameraRoute : BaseRoute("camera", R.string.camera_route)
    data object SettingsRoute : BaseRoute("settings", R.string.settings_route)
}
