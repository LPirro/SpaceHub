/*
 * SpaceHub - Designed and Developed by LPirro (Leonardo Pirro)
 * Copyright (C) 2023 Leonardo Pirro
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.spacehub.launcheslist.presentation

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spacehub.core.design.theme.SpacehubTheme
import com.spacehub.launcheslist.R
import com.spacehub.launcheslist.domain.model.LaunchFilter

@Composable
internal fun <T : LaunchFilter> MultiSelectFilterBottomSheetContent(
    title: String,
    options: List<T>,
    initialSelection: Set<T>,
    onConfirm: (Set<T>) -> Unit,
) {
    var pendingSelection by remember { mutableStateOf(initialSelection) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
            Button(onClick = { onConfirm(pendingSelection) }) {
                Text(text = stringResource(R.string.filter_confirm))
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f, fill = false),
        ) {
            items(options) { option ->
                val isSelected = option in pendingSelection
                ListItem(
                    headlineContent = { Text(text = option.name) },
                    trailingContent = if (isSelected) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    } else {
                        null
                    },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                    modifier = Modifier.clickable {
                        pendingSelection = if (option in pendingSelection) {
                            pendingSelection - option
                        } else {
                            pendingSelection + option
                        }
                    },
                )
            }
        }

        if (pendingSelection.isNotEmpty()) {
            TextButton(
                onClick = { pendingSelection = emptySet() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
            ) {
                Text(text = stringResource(R.string.filter_clear_selection))
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MultiSelectFilterBottomSheetContentPreview() {
    val sampleAgencies = listOf(
        LaunchFilter.Agency(id = "1", name = "SpaceX"),
        LaunchFilter.Agency(id = "2", name = "NASA"),
        LaunchFilter.Agency(id = "3", name = "Blue Origin"),
        LaunchFilter.Agency(id = "4", name = "Rocket Lab"),
    )
    val selectedAgencies = setOf(sampleAgencies[0], sampleAgencies[1])

    SpacehubTheme {
        MultiSelectFilterBottomSheetContent(
            title = "Agency",
            options = sampleAgencies,
            initialSelection = selectedAgencies,
            onConfirm = {},
        )
    }
}

