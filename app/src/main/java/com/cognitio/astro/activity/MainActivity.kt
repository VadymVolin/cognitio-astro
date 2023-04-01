package com.cognitio.astro.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cognitio.astro.ui.components.MainActivityLayout
import com.cognitio.astro.ui.theme.CognitioAstroTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CognitioAstroTheme {
                // A surface container using the 'background' color from the theme
                MainActivityLayout()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CognitioAstroTheme {
        MainActivityLayout()
    }
}