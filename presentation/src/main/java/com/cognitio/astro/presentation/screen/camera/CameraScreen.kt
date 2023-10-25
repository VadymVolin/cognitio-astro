package com.cognitio.astro.presentation.screen.camera

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OnImageSavedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.cognitio.astro.presentation.components.CognitioCameraPreview
import com.cognitio.astro.presentation.screen.common.DialogScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import okhttp3.internal.toImmutableList
import java.text.SimpleDateFormat
import java.util.Locale

private const val TAG: String = "CameraScreen"
private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

private val requiredPermissions: List<String> = mutableListOf(
    Manifest.permission.CAMERA
).apply {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
        add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}.toImmutableList()

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(setShowCameraScreen: (Boolean) -> Unit) {
    val context = LocalContext.current

    val dialogVisibilityState = remember { mutableStateOf(false) }
    val permissionState = rememberMultiplePermissionsState(requiredPermissions) { permissionsResult ->
        permissionsResult.forEach {
            if (requiredPermissions.contains(it.key) && !it.value) {
                setShowCameraScreen.invoke(false)
            }
        }
    }

    if (permissionState.allPermissionsGranted) {
        CameraDialog(context = context, setShowCameraScreen = setShowCameraScreen)
    } else {
        if (!dialogVisibilityState.value) {
            DialogScreen(dialogContent = {
                CameraPermissionMessageDialog(permissionState)
            }, setShowDialog = {
                setShowCameraScreen.invoke(false)
            }, showActionBar = false)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionMessageDialog(permissionState: MultiplePermissionsState) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.onSurface,
            text = getPermissionsRationaleText(
                permissionState.permissions, permissionState.shouldShowRationale
            ),
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal
            )
        )
        Button(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(MaterialTheme.colorScheme.primary),
            onClick = {
                permissionState.launchMultiplePermissionRequest()
            }) {
            Text(text = "OK",
                color = MaterialTheme.colorScheme.onPrimary,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Black
                ))
        }
    }
}

@Composable
fun CameraDialog(context: Context, setShowCameraScreen: (Boolean) -> Unit) {
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
        setShowCameraScreen.invoke(false)
    }
}

@Composable
fun CameraView(context: Context, lifecycleCameraController: LifecycleCameraController) {
    Box(modifier = Modifier.fillMaxSize()) {
        CognitioCameraPreview(lifecycleCameraController, Modifier.fillMaxSize())
        IconButton(
            onClick = {
                // Create time stamped name and MediaStore entry.
                val name =
                    SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Cognitio/")
                    }
                }

                // Create output options object which contains file + metadata
                val outputOptions = ImageCapture.OutputFileOptions.Builder(
                        context.contentResolver,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                    ).build()

                lifecycleCameraController.takePicture(outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            val msg = "Photo capture succeeded: ${outputFileResults.savedUri}"
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }

                        override fun onError(exception: ImageCaptureException) {
                            Log.e(TAG, "onError: Couldn't take a picture", exception)
                        }

                    })
            }, modifier = Modifier
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

@OptIn(ExperimentalPermissionsApi::class)
private fun getPermissionsRationaleText(
    permissions: List<PermissionState>, shouldShowRationale: Boolean
): String {
    val revokedPermissionsSize = permissions.size
    if (revokedPermissionsSize == 0) return ""

    val textToShow = StringBuilder().apply {
        append("The ")
    }

    for (i in permissions.indices) {
        textToShow.append(permissions[i].permission)
        when {
            revokedPermissionsSize > 1 && i == revokedPermissionsSize - 2 -> {
                textToShow.append(", and ")
            }

            i == revokedPermissionsSize - 1 -> {
                textToShow.append(" ")
            }

            else -> {
                textToShow.append(", ")
            }
        }
    }
    textToShow.append(if (revokedPermissionsSize == 1) "permission is" else "permissions are")
    textToShow.append(
        if (shouldShowRationale) {
            " important. Please grant all of them for the app to function properly."
        } else {
            " denied. The app cannot function without them."
        }
    )
    return textToShow.toString()
}

