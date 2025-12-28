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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.spacehub.core.ui.LightAndDarkPreviews
import com.spacehub.core.ui.composables.ErrorScreen
import com.spacehub.core.ui.composables.LaunchCard
import com.spacehub.core.ui.composables.NextLaunchCard
import com.spacehub.core.ui.composables.SpaceTopBar
import com.spacehub.core.ui.theme.SpacehubTheme
import com.spacehub.launches.R
import com.spacehub.launches.presentation.model.LaunchUi

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
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SpaceTopBar(
                text = stringResource(R.string.launches_topbar_title),
                scrollBehavior = scrollBehavior,
            )
        },
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
    val pagerState = rememberPagerState(pageCount = { upcomingLaunches.take(4).size })

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp),
        ) {
            HorizontalPager(
                state = pagerState,
                pageSpacing = 8.dp,
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) { page ->
                NextLaunchCard(
                    title = upcomingLaunches[page].title,
                    provider = upcomingLaunches[page].agency,
                    location = upcomingLaunches[page].location,
                    launchImageUrl = upcomingLaunches[page].launchImageUrl,
                    targetDateMillis = upcomingLaunches[page].netMillis,
                    onClick = {
                        onLaunchClicked.invoke(
                            upcomingLaunches[page].id,
                            upcomingLaunches[page].title,
                        )
                    },
                )
            }

            Header(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                title = stringResource(R.string.upcoming_launches),
                onViewAllClick = onUpcomingLaunchesViewAllClick,
            )

            upcomingLaunches.take(5).forEach { launch ->
                LaunchCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = launch.title,
                    agency = launch.agency,
                    dateTime = launch.dateTime,
                    status = launch.status,
                    launchImageUrl = launch.launchImageUrl,
                    onClick = { onLaunchClicked.invoke(launch.id, launch.title) },
                )
                Spacer(Modifier.height(12.dp))
            }

            Header(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                title = stringResource(R.string.past_launches),
                onViewAllClick = onPastLaunchesViewAllClick,
            )

            pastLaunches.take(5).forEach { launch ->
                LaunchCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = launch.title,
                    agency = launch.agency,
                    dateTime = launch.dateTime,
                    status = launch.status,
                    launchImageUrl = launch.launchImageUrl,
                    onClick = { onLaunchClicked.invoke(launch.id, launch.title) },
                )
                Spacer(Modifier.height(12.dp))
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
            .padding(vertical = 6.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
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

@LightAndDarkPreviews
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
