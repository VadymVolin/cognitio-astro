package com.cognitio.astro.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cognitio.astro.presentation.navigation.BaseRoute
import com.cognitio.astro.presentation.theme.CognitioAstroTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        internal val items = listOf(
            BaseRoute.PODRoute, BaseRoute.GalleryRoute, BaseRoute.SettingsRoute
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CognitioAstroTheme {
                MainActivityLayout(items)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CognitioAstroTheme {
        MainActivityLayout(items = MainActivity.items)
    }
}