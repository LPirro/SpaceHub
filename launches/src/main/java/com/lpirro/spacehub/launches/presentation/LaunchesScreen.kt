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

package com.lpirro.spacehub.launches.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lpirro.spacehub.core.R
import com.lpirro.spacehub.core.composables.SpaceTopBar
import com.lpirro.spacehub.core.model.TabItem
import com.lpirro.spacehub.core.ui.composables.ErrorScreen
import com.lpirro.spacehub.core.ui.composables.LaunchCard
import com.lpirro.spacehub.core.ui.theme.SpacehubTheme
import com.lpirro.spacehub.launches.domain.model.Launch
import com.lpirro.spacehub.launches.R as R2

@Composable
fun LaunchesScreen(
    viewModel: LaunchesViewModel = hiltViewModel(),
    onLaunchClicked: () -> Unit,
) {
    val uiStateUpcomingLaunches = viewModel.uiStateUpcomingLaunches.collectAsState()
    val uiStatePastLaunches = viewModel.uiStatePastLaunches.collectAsState()
    val isRefreshLoading = viewModel.isRefreshLoading.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabItems =
        listOf(
            TabItem(
                title = stringResource(com.lpirro.spacehub.launches.R.string.upcoming),
                unselectedIcon = ImageVector.vectorResource(id = R.drawable.rocket_outline),
                selectedIcon = ImageVector.vectorResource(id = R.drawable.rocket),
            ),
            TabItem(
                title = stringResource(com.lpirro.spacehub.launches.R.string.past),
                unselectedIcon = ImageVector.vectorResource(id = R.drawable.calendar_clock_outline),
                selectedIcon = ImageVector.vectorResource(id = R.drawable.calendar_clock),
            ),
        )

    val pagerState = rememberPagerState { tabItems.size }

    Scaffold(
        topBar = { SpaceTopBar(text = stringResource(R2.string.launches_topbar_title)) },
    ) { innerPadding ->

        LaunchedEffect(selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }

        LaunchedEffect(pagerState.currentPage) {
            selectedTabIndex = pagerState.currentPage
        }
        Column(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
            PrimaryTabRow(selectedTabIndex = selectedTabIndex) {
                tabItems.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = tabItem.title,
                                color =
                                    if (index == selectedTabIndex) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                            )
                        },
                        icon = {
                            Icon(
                                imageVector =
                                    if (index == selectedTabIndex) {
                                        tabItem.selectedIcon
                                    } else {
                                        tabItem.unselectedIcon
                                    },
                                tint =
                                    if (index == selectedTabIndex) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    },
                                contentDescription = tabItem.title,
                            )
                        },
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
            ) { index ->
                when (index) {
                    0 ->
                        LaunchContent(
                            state = uiStateUpcomingLaunches.value,
                            onLaunchClicked = onLaunchClicked,
                            onTryAgainClicked = viewModel::getUpcomingLaunches,
                            onRefresh = { viewModel.getUpcomingLaunches(true) },
                            isRefreshing = isRefreshLoading.value,
                        )

                    1 ->
                        LaunchContent(
                            state = uiStatePastLaunches.value,
                            onLaunchClicked = onLaunchClicked,
                            onTryAgainClicked = viewModel::getPastLaunches,
                            onRefresh = { viewModel.getPastLaunches(true) },
                            isRefreshing = isRefreshLoading.value,
                        )
                }
            }
        }
    }
}

@Composable
fun LaunchContent(
    state: LaunchesUiState,
    onLaunchClicked: () -> Unit,
    onTryAgainClicked: () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
) {
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
            LaunchList(
                launches = state.launches,
                onLaunchClicked = onLaunchClicked,
                onRefresh = onRefresh,
                isRefreshing = isRefreshing,
            )
        }
    }
}

@Composable
fun LaunchList(
    launches: List<Launch>,
    onLaunchClicked: () -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(launches) { launch ->
                LaunchCard(
                    title = launch.name,
                    agency = launch.launchServiceProvider.name,
                    location = launch.pad.location.name,
                    dateTime = launch.netDisplay ?: "",
                    netMillis = launch.netMillis ?: 0,
                    status = launch.status,
                    launchImageUrl = launch.image,
                    onClick = onLaunchClicked,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LaunchesContentPreview(
    @PreviewParameter(SampleLaunchesProvider::class) launches: List<Launch>,
) {
    SpacehubTheme {
        LaunchList(
            launches = launches,
            onLaunchClicked = {},
            onRefresh = {},
            isRefreshing = false,
        )
    }
}
