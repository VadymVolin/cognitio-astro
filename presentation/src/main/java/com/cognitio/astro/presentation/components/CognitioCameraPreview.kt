package com.cognitio.astro.presentation.components

import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CognitioCameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier
) {
    val cameraLifecycle = LocalLifecycleOwner.current
    AndroidView(factory = {
        PreviewView(it).apply {
            this.controller = controller
            controller.bindToLifecycle(cameraLifecycle)
        }
    }, modifier = modifier)
}