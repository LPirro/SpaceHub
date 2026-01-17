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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spacehub.core.design.theme.SpacehubTheme

@Composable
fun InfoItems(details: List<Pair<String, String>>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        details.forEachIndexed { index, (label, value) ->
            ItemRow(label = label, value = value)
            if (index != details.size - 1) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun ItemRow(
    label: String,
    value: String,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = horizontalArrangement,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.outline,
        )
        Text(
            modifier = Modifier.weight(2f, fill = false),
            text = value,
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLaunchDetails() {
    val items = listOf(
        "Name" to "Space Launch Complex 40 Space Launch Complex 40 Space Launch",
        "Location" to "Cape Canaveral, FL, USA",
        "Total Launches" to "162",
    )
    SpacehubTheme {
        InfoItems(details = items)
    }
}
