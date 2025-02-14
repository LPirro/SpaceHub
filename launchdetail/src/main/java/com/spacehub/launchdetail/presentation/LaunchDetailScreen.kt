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

package com.spacehub.launchdetail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.lpirro.spacehub.core.ui.composables.SpaceTopBar
import com.lpirro.spacehub.core.ui.theme.SpacehubTheme
import com.spacehub.launchdetail.R
import com.spacehub.launchdetail.presentation.overview.LaunchDetailOverview
import com.spacehub.launchdetail.presentation.overview.LaunchDetailOverviewViewModel
import com.spacehub.launchdetail.presentation.overview.LaunchDetailOverviewViewModel.Factory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDetailScreen(
    launchId: String,
    title: String,
    onBackPressed: (() -> Unit)? = null,
    viewModel: LaunchDetailOverviewViewModel = hiltViewModel(
        creationCallback = { factory: Factory ->
            factory.create(launchId = launchId)
        },
    ),
) {
    val uiState by viewModel.uiState.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabItems = listOf(
        stringResource(R.string.launch_detail_tab_overview),
        stringResource(R.string.launch_detail_tab_mission),
        stringResource(R.string.launch_detail_tab_vehicle),
    )

    val pagerState = rememberPagerState { tabItems.size }

    Scaffold(
        topBar = {
            SpaceTopBar(
                text = title,
                showBackArrow = true,
                onBackClick = onBackPressed,
            )
        },
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
                                text = tabItem,
                                color = if (index == selectedTabIndex) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
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
                    0 -> LaunchDetailOverview(uiState)
                    1 -> Text("Mission")
                    2 -> Text("Vehicle")
                }
            }
        }
    }
}

@Preview
@Composable
private fun LaunchDetailScreenPreview() {
    SpacehubTheme {
        LaunchDetailScreen(launchId = "1", title = "Launch Name", onBackPressed = {})
    }
}
