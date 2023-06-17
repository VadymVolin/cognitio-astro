package com.cognitio.astro.presentation.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            //        Image(bitmap = null, contentDescription = )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = pictureOfTheDay.date.trim(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = pictureOfTheDay.author.trim(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f),
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = pictureOfTheDay.title, modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(), maxLines = 2
            )
            Text(
                text = pictureOfTheDay.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}