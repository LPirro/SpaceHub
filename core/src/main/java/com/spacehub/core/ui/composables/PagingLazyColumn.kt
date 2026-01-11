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
package com.spacehub.core.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey

@Composable
fun <T : Any> PagingLazyColumn(
    items: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp),
    key: ((T) -> Any)? = null,
    onRefreshLoading: @Composable () -> Unit = { DefaultLoadingIndicator() },
    onRefreshError: @Composable (Throwable) -> Unit = {},
    onAppendLoading: @Composable LazyItemScope.() -> Unit = { DefaultAppendLoadingIndicator() },
    onAppendError: @Composable LazyItemScope.(Throwable) -> Unit = {},
    emptyContent: @Composable () -> Unit = {},
    itemContent: @Composable LazyItemScope.(index: Int, item: T) -> Unit,
) {
    when (val refreshState = items.loadState.refresh) {
        is LoadState.Loading -> {
            onRefreshLoading()
        }

        is LoadState.Error -> {
            onRefreshError(refreshState.error)
        }

        is LoadState.NotLoading -> {
            if (items.itemCount == 0) {
                emptyContent()
            } else {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    state = state,
                    contentPadding = contentPadding,
                    verticalArrangement = verticalArrangement,
                ) {
                    items(
                        count = items.itemCount,
                        key = if (key != null) items.itemKey { key(it) } else null,
                    ) { index ->
                        val item = items[index]
                        if (item != null) {
                            itemContent(index, item)
                        }
                    }

                    when (val appendState = items.loadState.append) {
                        is LoadState.Loading -> {
                            item { onAppendLoading() }
                        }

                        is LoadState.Error -> {
                            item { onAppendError(appendState.error) }
                        }

                        is LoadState.NotLoading -> Unit
                    }
                }
            }
        }
    }
}

@Composable
private fun DefaultLoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(),
    )
}

@Composable
private fun DefaultAppendLoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}