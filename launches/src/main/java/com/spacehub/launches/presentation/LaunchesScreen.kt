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

package com.spacehub.launches.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spacehub.core.ui.composables.ErrorScreen
import com.spacehub.core.ui.composables.LaunchCard
import com.spacehub.core.ui.composables.SpaceTopBar
import com.spacehub.core.ui.theme.SpacehubTheme
import com.spacehub.launches.presentation.model.LaunchUi
import com.spacehub.launches.R

@Composable
fun LaunchesScreen(
    viewModel: LaunchesViewModel = hiltViewModel(),
    onLaunchClicked: (id: String, name: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshLoading by viewModel.isRefreshLoading.collectAsState()

    LaunchesScreenContent(
        state = uiState,
        onLaunchClicked = onLaunchClicked,
        onTryAgainClicked = viewModel::getLaunches,
        onRefresh = viewModel::refresh,
        isRefreshing = isRefreshLoading,
    )
}

@Composable
fun LaunchesScreenContent(
    state: LaunchesUiState,
    onLaunchClicked: (id: String, name: String) -> Unit,
    onTryAgainClicked: () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
) {
    Scaffold(
        topBar = { SpaceTopBar(text = stringResource(R.string.launches_topbar_title)) },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
            when (state) {
                LaunchesUiState.Error -> {
                    ErrorScreen(onTryAgainClicked = onTryAgainClicked)
                }

                is LaunchesUiState.Loading -> {
                    CircularProgressIndicator(
                        Modifier
                            .fillMaxSize()
                            .wrapContentSize(),
                    )
                }

                is LaunchesUiState.Success -> {
                    LaunchScreenSuccess(
                        upcomingLaunches = state.upcomingLaunches,
                        pastLaunches = state.pastLaunches,
                        onLaunchClicked = onLaunchClicked,
                        onRefresh = onRefresh,
                        isRefreshing = isRefreshing,
                        onUpcomingLaunchesViewAllClick = {},
                        onPastLaunchesViewAllClick = {},
                    )
                }
            }
        }
    }
}

@Composable
fun LaunchScreenSuccess(
    upcomingLaunches: List<LaunchUi>,
    pastLaunches: List<LaunchUi>,
    onLaunchClicked: (id: String, name: String) -> Unit,
    onUpcomingLaunchesViewAllClick: () -> Unit,
    onPastLaunchesViewAllClick: () -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Header(
                    title = stringResource(R.string.upcoming_launches),
                    onViewAllClick = onUpcomingLaunchesViewAllClick,
                )
            }

            items(upcomingLaunches.take(5)) { launch ->
                LaunchCard(
                    title = launch.title,
                    agency = launch.agency,
                    dateTime = launch.dateTime,
                    status = launch.status,
                    launchImageUrl = launch.launchImageUrl,
                    onClick = { onLaunchClicked.invoke(launch.id, launch.title) },
                )
            }

            item {
                Header(
                    title = stringResource(R.string.past_launches),
                    onViewAllClick = onPastLaunchesViewAllClick,
                )
            }

            items(pastLaunches.take(5)) { launch ->
                LaunchCard(
                    title = launch.title,
                    agency = launch.agency,
                    dateTime = launch.dateTime,
                    status = launch.status,
                    launchImageUrl = launch.launchImageUrl,
                    onClick = { onLaunchClicked.invoke(launch.id, launch.title) },
                )
            }
        }
    }
}

@Composable
fun Header(
    title: String,
    onViewAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        TextButton(
            onClick = onViewAllClick,
        ) {
            Text(text = stringResource(R.string.view_all))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LaunchesScreenContentPreview(
    @PreviewParameter(LaunchesScreenPreviewProvider::class) state: LaunchesUiState,
) {
    SpacehubTheme {
        LaunchesScreenContent(
            state = state,
            onLaunchClicked = { _, _ -> },
            onTryAgainClicked = {},
            onRefresh = {},
            isRefreshing = false,
        )
    }
}
