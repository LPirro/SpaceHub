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
@file:OptIn(ExperimentalMaterial3Api::class)

package com.spacehub.launcheslist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.spacehub.common.models.domain.LaunchType
import com.spacehub.core.ui.composables.ErrorScreen
import com.spacehub.core.ui.composables.LaunchCard
import com.spacehub.core.ui.composables.SpaceTopBar
import com.spacehub.launcheslist.R
import com.spacehub.launcheslist.presentation.model.LaunchListItemUiModel

@Composable
fun LaunchesListScreen(
    launchType: LaunchType,
    viewModel: LaunchesListViewModel = hiltViewModel<LaunchesListViewModel, LaunchesListViewModel.Factory>(
        key = launchType.name,
        creationCallback = { factory -> factory.create(launchType) },
    ),
    onLaunchClicked: (id: String, name: String) -> Unit,
    onBackClick: () -> Unit,
) {
    val launches = viewModel.launches.collectAsLazyPagingItems()

    LaunchesListScreenContent(
        launchType = launchType,
        launches = launches,
        onLaunchClicked = onLaunchClicked,
        onBackClick = onBackClick,
    )
}

@Composable
fun LaunchesListScreenContent(
    launchType: LaunchType,
    launches: LazyPagingItems<LaunchListItemUiModel>,
    onLaunchClicked: (id: String, name: String) -> Unit,
    onBackClick: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val title = when (launchType) {
        LaunchType.UPCOMING -> stringResource(R.string.upcoming_launches_title)
        LaunchType.PAST -> stringResource(R.string.past_launches_title)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SpaceTopBar(
                text = title,
                scrollBehavior = scrollBehavior,
                showBackArrow = true,
                onBackClick = onBackClick,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
        ) {
            when (launches.loadState.refresh) {
                is LoadState.Loading -> {
                    CircularProgressIndicator(
                        Modifier
                            .fillMaxSize()
                            .wrapContentSize(),
                    )
                }

                is LoadState.Error -> {
                    ErrorScreen(onTryAgainClicked = { launches.retry() })
                }

                is LoadState.NotLoading -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(
                            count = launches.itemCount,
                            key = launches.itemKey { it.id },
                        ) { index ->
                            val launch = launches[index]
                            if (launch != null) {
                                LaunchCard(
                                    title = launch.title,
                                    agency = launch.agency,
                                    dateTime = launch.dateTime,
                                    status = launch.status,
                                    launchImageUrl = launch.launchImageUrl,
                                    onClick = { onLaunchClicked(launch.id, launch.title) },
                                )
                            }
                        }

                        if (launches.loadState.append is LoadState.Loading) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        if (launches.loadState.append is LoadState.Error) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    ErrorScreen(onTryAgainClicked = { launches.retry() })
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding()))
                        }
                    }
                }
            }
        }
    }
}
