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

package com.spacehub.core.design.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.spacehub.core.common.util.RemovePaddingTransformation
import com.spacehub.core.design.theme.SpacehubTheme

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    title: String,
    padding: Dp = 16.dp,
    headerImageUrl: String? = null,
    contentImageUrl: String? = null,
    content: @Composable () -> Unit,
) {
    val cornerRadius = 12.dp
    val shape = RoundedCornerShape(cornerRadius)

    Column(
        modifier = modifier
            .clip(shape)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = shape,
            ),
    ) {
        headerImageUrl?.let {
            AsyncImage(
                model =
                ImageRequest.Builder(LocalContext.current)
                    .data(it)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius))
                    .height(190.dp),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        }

        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Column(modifier = Modifier.padding(start = padding, end = padding, bottom = padding)) {
            contentImageUrl?.let {
                AsyncImage(
                    model =
                    ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .crossfade(true)
                        .transformations(RemovePaddingTransformation())
                        .build(),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .height(20.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
            }
            content()
        }
    }
}

@Preview
@Composable
private fun InfoCardPreview() {
    SpacehubTheme {
        InfoCard(title = "Title", modifier = Modifier) {
            Text(text = "Description")
        }
    }
}

@Preview
@Composable
private fun InfoCardInfoItemsPreview() {
    val items = listOf(
        "Name" to "Space Launch Complex 40",
        "Location" to "Cape Canaveral, FL, USA",
        "Total Launches" to "162",
    )
    SpacehubTheme {
        InfoCard(title = "Title") {
            InfoItems(details = items)
        }
    }
}
