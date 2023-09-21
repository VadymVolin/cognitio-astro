package com.cognitio.astro.presentation.screen.nasa.dialog

import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cognitio.astro.domain.model.PictureOfTheDay
import com.cognitio.astro.presentation.common.DrawableUtils
import com.cognitio.astro.presentation.screen.common.DialogScreen
import com.cognitio.astro.presentation.theme.CognitioAstroTheme

@Composable
fun PictureOfTheDayDetailsDialog(pictureOfTheDay: PictureOfTheDay?) {
    if (pictureOfTheDay == null) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material.Text(
                text = "No data",
                color = MaterialTheme.colorScheme.error
            )
        }
        return
    }

    val isTitlePresent = !TextUtils.isEmpty(pictureOfTheDay.title.trim())
    val isAuthorsPresent = !TextUtils.isEmpty(pictureOfTheDay.author.trim())
    val isDescriptionPresent = !TextUtils.isEmpty(pictureOfTheDay.description.trim())
    val isDatePresent = !TextUtils.isEmpty(pictureOfTheDay.date.trim())
    Box {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(pictureOfTheDay.imageUrl)
                .fallback(DrawableUtils.getImagePlaceholderId())
                .placeholder(DrawableUtils.getImagePlaceholderId())
                .error(DrawableUtils.getImagePlaceholderId()).crossfade(true).build(),
            placeholder = DrawableUtils.getImagePlaceholder(),
            fallback = DrawableUtils.getImagePlaceholder(),
            error = DrawableUtils.getImagePlaceholder(),
            contentDescription = pictureOfTheDay.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .background(MaterialTheme.colorScheme.surface)
        )
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        0f to Color.Transparent, 1f to MaterialTheme.colorScheme.surface
                    )
                )
        )
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.aspectRatio(.55f, true))
            if (isTitlePresent) {
                Text(
                    text = pictureOfTheDay.title.trim(),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2,
                    textAlign = TextAlign.Justify,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                )
            }
            if (isAuthorsPresent) {
                Text(
                    text = pictureOfTheDay.author.trim(),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2,
                    textAlign = TextAlign.Justify,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                )
            }
            if (isDatePresent) {
                Text(
                    text = pictureOfTheDay.date.trim(),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Justify,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                )
            }
            if (isDescriptionPresent) {
                Text(
                    text = pictureOfTheDay.description,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Justify,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 26.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PODPreview() {
    CognitioAstroTheme {
        val item = PictureOfTheDay(
            title = LoremIpsum(words = 4).values.joinToString(" "),
            description = LoremIpsum(words = 200).values.joinToString(" "),
            author = LoremIpsum(words = 7).values.joinToString(" "),
            date = LoremIpsum(words = 3).values.joinToString(" "),
            imageUrl = "https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e001840/GSFC_20171208_Archive_e001840~orig.jpg?w=1736&h=1933&fit=crop&crop=faces%2Cfocalpoint",
            mediaType = PictureOfTheDay.MediaType.IMAGE
        )
        DialogScreen(dialogContent = { PictureOfTheDayDetailsDialog(pictureOfTheDay = item) },
            setShowDialog = {})
    }
}