package com.cognitio.astro.presentation.screen.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.cognitio.astro.presentation.R


@Composable
fun DialogScreen(
    dialogContent: @Composable () -> Unit,
    dialogTitle: String?,
    setShowDialog: (Boolean) -> Unit,
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colors.background
                ),
            color = MaterialTheme.colors.background
        ) {
            Box {
                dialogContent.invoke()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    MaterialTheme.colors.background,
                                    Color.Transparent
                                ), startY = 0F, endY = 1F
                            )
                        ),
                    horizontalArrangement = if (dialogTitle == null) Arrangement.End else Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    dialogTitle?.let {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = it,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close_icon_text),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                            .padding(4.dp)
                            .clickable { setShowDialog(false) }
                    )
                }
            }
        }
    }
}