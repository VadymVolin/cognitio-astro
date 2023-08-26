package com.cognitio.astro.presentation.screen.nasa.dialog

import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cognitio.astro.domain.model.PictureOfTheDay
import com.cognitio.astro.presentation.common.DrawableUtils
import com.cognitio.astro.presentation.screen.common.DialogScreen
import com.cognitio.astro.presentation.theme.CognitioAstroTheme

@Composable
fun PictureOfTheDayDetailsDialog(pictureOfTheDay: PictureOfTheDay) {
    val isAuthorsPresent = TextUtils.isEmpty(pictureOfTheDay.author.trim())
    val isDescriptionPresent = TextUtils.isEmpty(pictureOfTheDay.description.trim())
    val isDatePresent = TextUtils.isEmpty(pictureOfTheDay.date.trim())
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Box {
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
                    .height(280.dp)
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .background(MaterialTheme.colors.background)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .padding(top = 248.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                MaterialTheme.colors.background
                            )
                        )
                    )
            ) {}
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (!isAuthorsPresent) {
                Text(
                    text = pictureOfTheDay.author.trim(),
                    modifier = Modifier
                        .padding(bottom = if (!isDatePresent) 4.dp else 8.dp)
                        .fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (!isDatePresent) {
                Text(
                    text = pictureOfTheDay.date.trim(),
                    modifier = Modifier
                        .padding(bottom = if (!isDescriptionPresent) 4.dp else 16.dp)
                        .fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (!isDescriptionPresent) {
                Text(
                    text = pictureOfTheDay.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Justify
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
        DialogScreen(
            dialogContent = { PictureOfTheDayDetailsDialog(pictureOfTheDay = item) },
            dialogTitle = item.title,
            setShowDialog = {}
        )
    }
}