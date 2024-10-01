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

package com.lpirro.spacehub.core.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lpirro.spacehub.core.R

@Composable
fun SearchAppBar(
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.search_generic),
    textQuery: String,
    isLoading: Boolean,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        color = MaterialTheme.colorScheme.surface,
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textQuery,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(0.7f),
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            textStyle = TextStyle(fontSize = MaterialTheme.typography.bodySmall.fontSize),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(0.7f),
                    onClick = { },
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.content_description_search_icon),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            },
            trailingIcon = {
                if (isLoading) {
                    CircularProgressIndicator(
                        Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                } else {
                    IconButton(
                        onClick = {
                            if (textQuery.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked.invoke()
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.content_description_close_icon),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchClicked.invoke(textQuery) },

            ),
        )
    }
}

@Preview
@Composable
private fun SearchAppBarPreview() {
    SearchAppBar(
        textQuery = "SpaceX Starship launch",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {},
        isLoading = false,
    )
}
