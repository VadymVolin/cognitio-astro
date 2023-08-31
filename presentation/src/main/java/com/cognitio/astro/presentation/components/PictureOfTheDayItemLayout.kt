package com.cognitio.astro.presentation.components

import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cognitio.astro.domain.model.PictureOfTheDay
import com.cognitio.astro.presentation.common.DrawableUtils

@Composable
fun PictureOfTheDayItemLayout(
    modifier: Modifier,
    pictureOfTheDay: PictureOfTheDay,
    onItemClick: (PictureOfTheDay) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface)
        .clickable { onItemClick(pictureOfTheDay) }
    ) {
        val isTitlePresent = TextUtils.isEmpty(pictureOfTheDay.title.trim())
        val isAuthorsPresent = TextUtils.isEmpty(pictureOfTheDay.author.trim())
        val isDescriptionPresent = TextUtils.isEmpty(pictureOfTheDay.description.trim())
        val isDatePresent = TextUtils.isEmpty(pictureOfTheDay.date.trim())

        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pictureOfTheDay.imageUrl)
                    .fallback(DrawableUtils.getImagePlaceholderId())
                    .placeholder(DrawableUtils.getImagePlaceholderId())
                    .error(DrawableUtils.getImagePlaceholderId())
                    .crossfade(true)
                    .build(),
                placeholder = DrawableUtils.getImagePlaceholder(),
                fallback = DrawableUtils.getImagePlaceholder(),
                error = DrawableUtils.getImagePlaceholder(),
                contentDescription = pictureOfTheDay.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(240.dp)
                    .padding(bottom = 12.dp)
                    .background(MaterialTheme.colorScheme.surface)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                if (!isTitlePresent) {
                    Text(
                        text = pictureOfTheDay.title, modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = if (!isAuthorsPresent) 8.dp else 4.dp)
                            .wrapContentHeight(),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Black
                        )
                    )
                }
                if (!isAuthorsPresent) {
                    Text(
                        text = pictureOfTheDay.author.trim(),
                        modifier = Modifier
                            .padding(bottom = if (!isDescriptionPresent) 8.dp else 4.dp)
                            .fillMaxWidth(),
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                if (!isDescriptionPresent) {
                    Text(
                        text = pictureOfTheDay.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = if (!isDatePresent) 8.dp else 4.dp)
                            .wrapContentHeight(),
                        maxLines = 3,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Justify,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
                if (!isDatePresent) {
                    Text(
                        text = pictureOfTheDay.date.trim(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.End,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
