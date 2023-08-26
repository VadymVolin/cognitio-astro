package com.cognitio.astro.presentation.screen.common

import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cognitio.astro.presentation.R


@Composable
fun DialogScreen(
    dialogContent: @Composable () -> Unit,
    dialogTitle: String?,
    setShowDialog: (Boolean) -> Unit,
) {
    Dialog(properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { setShowDialog(false) }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.surface
                ),
        ) {
            Box {
                dialogContent.invoke()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(64.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                1f to MaterialTheme.colorScheme.surface, 0.2f to MaterialTheme.colorScheme.surface
                            )
                        ),
                    horizontalArrangement = if (dialogTitle == null) Arrangement.End else Arrangement.SpaceBetween
                ) {
                    dialogTitle?.let {
                        Text(
                            modifier = Modifier
                                .height(40.dp)
                                .padding(start = 12.dp, top = 12.dp),
                            text = it,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        )
                    }
                    Icon(imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close_icon_text),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(top = 12.dp, end = 12.dp)
                            .clickable { setShowDialog(false) })
                }
            }
        }
    }
}