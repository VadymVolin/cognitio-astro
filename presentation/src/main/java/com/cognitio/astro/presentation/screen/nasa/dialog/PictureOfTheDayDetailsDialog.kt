package com.cognitio.astro.presentation.screen.nasa.dialog

import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
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
        Text(text = "No data", modifier = Modifier.fillMaxSize())
        return
    }

    val isAuthorsPresent = TextUtils.isEmpty(pictureOfTheDay.author.trim())
    val isDescriptionPresent = TextUtils.isEmpty(pictureOfTheDay.description.trim())
    val isDatePresent = TextUtils.isEmpty(pictureOfTheDay.date.trim())

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier.padding(bottom = 16.dp)) {
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
                    .height(400.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(400.dp)
                    .padding(top = 200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                                0f to Color.Transparent, 1f to MaterialTheme.colorScheme.surface
                        )
                    )
            )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 360.dp)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (!isAuthorsPresent) {
                Text(
                    text = pictureOfTheDay.author.trim(),
                    modifier = Modifier
                        .padding(bottom = if (!isDatePresent) 8.dp else 12.dp)
                        .fillMaxWidth(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            if (!isDatePresent) {
                Text(
                    text = pictureOfTheDay.date.trim(),
                    modifier = Modifier
                        .padding(bottom = if (!isDescriptionPresent) 8.dp else 16.dp)
                        .fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            if (!isDescriptionPresent) {
                Text(
                    text = pictureOfTheDay.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Justify,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
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
            dialogTitle = item.title,
            setShowDialog = {})
    }
}