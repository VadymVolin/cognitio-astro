package com.cognitio.astro.presentation.screen.camera

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OnImageSavedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.cognitio.astro.presentation.components.CognitioCameraPreview
import com.cognitio.astro.presentation.screen.common.DialogScreen
import java.text.SimpleDateFormat
import java.util.Locale

private const val TAG: String = "CameraScreen"
private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

private val requiredPermissions: Array<String> =
    mutableListOf(
        Manifest.permission.CAMERA
    ).apply {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }.toTypedArray()

var activityResultLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>? = null

@Composable
fun CameraScreen(navigationController: NavHostController) {
    val context = LocalContext.current

    val permissionGrantedState = remember { mutableStateOf(false) }

    activityResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var arePermissionsGranted = true
        permissions.entries.forEach {
            if (it.key in requiredPermissions && !it.value) {
                arePermissionsGranted = false
            }
        }
        if (!arePermissionsGranted) {
            navigationController.navigateUp()
            return@rememberLauncherForActivityResult
        } else {
            permissionGrantedState.value = true
        }
    }

    // Request camera permissions
    if (allPermissionsGranted() && !permissionGrantedState.value) {
        CameraDialog(context = context, navigationController = navigationController)
    } else {
        LaunchedEffect(true) {
            requestPermissions()
        }
    }
}

@Composable
fun CameraDialog(context: Context, navigationController: NavHostController) {
    val lifecycleCameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }

    val dialogVisibilityState = remember { mutableStateOf(true) }

    if (dialogVisibilityState.value) {
        DialogScreen(dialogContent = { CameraView(context, lifecycleCameraController) },
            showActionBar = false,
            setShowDialog = {
                dialogVisibilityState.value = it
            })
    } else {
        navigationController.navigateUp()
    }
}

@Composable
fun CameraView(context: Context, lifecycleCameraController: LifecycleCameraController) {
    Box(Modifier.fillMaxSize()) {
        CognitioCameraPreview(lifecycleCameraController, Modifier.matchParentSize())
        IconButton(
            onClick = {
                // Create time stamped name and MediaStore entry.
                val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                    .format(System.currentTimeMillis())
                Log.d(TAG, "CameraView: $name")
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                        put(MediaStore.Images.Media.RELATIVE_PATH, "Cognitio/")
                    }
                }

                // Create output options object which contains file + metadata
                val outputOptions = ImageCapture.OutputFileOptions
                    .Builder(
                        context.contentResolver,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                    )
                    .build()

                lifecycleCameraController.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            val msg = "Photo capture succeeded: ${outputFileResults.savedUri}"
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            Log.d(TAG, msg)
                        }

                        override fun onError(exception: ImageCaptureException) {
                            Log.e(TAG, "onError: Couldn't take a picture", exception)
                        }

                    }
                )
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(30.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Camera,
                contentDescription = Icons.Filled.PhotoCamera.name,
                modifier = Modifier.size(52.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

private fun requestPermissions() {
    activityResultLauncher?.launch(requiredPermissions)
}

@Composable
private fun allPermissionsGranted() = requiredPermissions.all {
    ContextCompat.checkSelfPermission(LocalContext.current, it) == PackageManager.PERMISSION_GRANTED
}