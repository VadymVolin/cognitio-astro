package com.cognitio.astro.presentation.activity

import android.content.Intent
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
        private val TAG = MainActivity::class.java.name

        internal val items = listOf(
            BaseRoute.PODRoute, BaseRoute.GalleryRoute, BaseRoute.CameraRoute, BaseRoute.SettingsRoute
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CognitioAstroTheme {
        MainActivityLayout(items = MainActivity.items)
    }
}