package com.cognitio.astro.presentation.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cognitio.astro.domain.model.PictureOfTheDay

@Composable
fun PictureOfTheDayItem(
    pictureOfTheDay: PictureOfTheDay,
    onItemClick: (PictureOfTheDay) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick(pictureOfTheDay) }
        .padding(16.dp)
    ) {
        Log.d("FORTRA", "PictureOfTheDayItem: ${pictureOfTheDay.description}")
//        Image(bitmap = null, contentDescription = )
        Text(text = pictureOfTheDay.date)
        Text(text = pictureOfTheDay.title, modifier = Modifier.fillMaxWidth().wrapContentHeight(), maxLines = 2)
        Text(text = pictureOfTheDay.description, modifier = Modifier.fillMaxWidth().wrapContentHeight(), maxLines = 3)
        Text(text = pictureOfTheDay.author)
    }
}