/*
 *
 *  * SpaceHub - Designed and Developed by LPirro (Leonardo Pirro)
 *  * Copyright (C) 2023 Leonardo Pirro
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.spacehub.core.ui.composables

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.spacehub.common.models.domain.Status
import com.spacehub.core.R
import com.spacehub.core.ui.theme.SpacehubTheme

@Composable
fun LaunchCard(
    modifier: Modifier = Modifier,
    title: String,
    agency: String,
    dateTime: String,
    status: Status,
    launchImageUrl: String?,
    onClick: () -> Unit,
) {
    val imageRequest = rememberImageRequest(launchImageUrl)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick.invoke() },
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {

            AsyncImage(
                model = imageRequest,
                modifier = Modifier
                    .size(width = 79.dp, height = 80.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 2.dp)
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(4.dp))

                LaunchInfoRow(
                    icon = ImageVector.vectorResource(id = R.drawable.domain),
                    text = agency,
                )

                Spacer(modifier = Modifier.height(6.dp))

                LaunchInfoRow(
                    icon = ImageVector.vectorResource(id = R.drawable.calendar_blank_outline),
                    text = dateTime,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            LaunchStatus(status = status)
        }
    }
}

@Composable
private fun rememberImageRequest(data: String?): ImageRequest {
    val context = LocalContext.current
    val tintColor = MaterialTheme.colorScheme.inverseOnSurface.toArgb()

    val placeholderDrawable = remember(tintColor) {
        AppCompatResources.getDrawable(
            context,
            R.drawable.image_placeholder,
        )?.apply {
            setTint(tintColor)
        }
    }

    return remember(data, placeholderDrawable) {
        ImageRequest.Builder(context)
            .data(data)
            .crossfade(true)
            .error(placeholderDrawable)
            .placeholder(placeholderDrawable)
            .build()
    }
}

@Composable
private fun LaunchInfoRow(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        androidx.compose.material3.Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun LaunchCardPreview() {
    SpacehubTheme {
        LaunchCard(
            title = "Starship Flight 4",
            agency = "SpaceX",
            dateTime = "1 Nov 2025 • 4:30 pm",
            status = Status.Go(name = "Go", abbrev = "GO", description = "description"),
            launchImageUrl = "",
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun LaunchCardLongTitlePreview() {
    SpacehubTheme {
        LaunchCard(
            title = "SpaceX Starship TestFlight number 2",
            agency = "SpaceX",
            dateTime = "24 Lug '23 • 19:00",
            status = Status.Go(name = "Go", abbrev = "GO", description = "description"),
            launchImageUrl = "",
            onClick = {},
        )
    }
}