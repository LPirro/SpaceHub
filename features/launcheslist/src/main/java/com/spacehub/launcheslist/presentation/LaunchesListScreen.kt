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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.spacehub.common.models.domain.LaunchType
import com.spacehub.core.design.LightAndDarkPreviews
import com.spacehub.core.design.composables.ErrorScreen
import com.spacehub.core.design.composables.LaunchCard
import com.spacehub.core.design.composables.PagingLazyColumn
import com.spacehub.core.design.composables.SpaceFilterChip
import com.spacehub.core.design.composables.SpaceTopBar
import com.spacehub.core.design.theme.SpacehubTheme
import com.spacehub.launcheslist.R
import com.spacehub.launcheslist.presentation.model.LaunchListItemUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

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
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LaunchesListScreenEffect.NavigateToLaunchDetail -> {
                    onLaunchClicked(effect.id, effect.name)
                }

                is LaunchesListScreenEffect.NavigateBack -> {
                    onBackClick()
                }
            }
        }
    }

    LaunchesListScreenContent(
        launchType = launchType,
        launches = launches,
        uiState = uiState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun LaunchesListScreenContent(
    launchType: LaunchType,
    launches: LazyPagingItems<LaunchListItemUiModel>,
    uiState: LaunchesListUiState,
    onEvent: (LaunchesListScreenEvent) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val bottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val title = when (launchType) {
        LaunchType.UPCOMING -> stringResource(R.string.upcoming_launches_title)
        LaunchType.PAST -> stringResource(R.string.past_launches_title)
    }

    val topAppBarColors = TopAppBarDefaults.topAppBarColors()
    val isScrolled = scrollBehavior.state.overlappedFraction > 0.01f
    val targetColor = if (isScrolled) {
        topAppBarColors.scrolledContainerColor
    } else {
        topAppBarColors.containerColor
    }
    val appBarContainerColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "appBarContainerColor",
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SpaceTopBar(
                text = title,
                scrollBehavior = scrollBehavior,
                showBackArrow = true,
                onBackClick = { onEvent(LaunchesListScreenEvent.BackClick) },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(appBarContainerColor)
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SpaceFilterChip(
                    label = stringResource(R.string.filter_agency),
                    selected = uiState.selectedAgencies.isNotEmpty(),
                    onClick = { onEvent(LaunchesListScreenEvent.AgencyFilterClick) },
                )
                SpaceFilterChip(
                    label = stringResource(R.string.filter_location),
                    selected = uiState.selectedLocations.isNotEmpty(),
                    onClick = { onEvent(LaunchesListScreenEvent.LocationFilterClick) },
                )
            }

            PagingLazyColumn(
                items = launches,
                contentPadding = PaddingValues(bottom = innerPadding.calculateBottomPadding()),
                key = { item: LaunchListItemUiModel -> item.id },
                onRefreshError = { ErrorScreen(onTryAgainClicked = { launches.retry() }) },
                onAppendError = { ErrorScreen(onTryAgainClicked = { launches.retry() }) },
                itemContent = { index, launch ->
                    LaunchCard(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = if (index == 0) 10.dp else 0.dp,
                        ),
                        title = launch.title,
                        agency = launch.agency,
                        dateTime = launch.dateTime,
                        status = launch.status,
                        launchImageUrl = launch.launchImageUrl,
                        onClick = { onEvent(LaunchesListScreenEvent.LaunchClick(launch.id, launch.title)) },
                    )
                },
            )
        }
    }

    if (uiState.activeBottomSheet != ActiveBottomSheet.None) {
        ModalBottomSheet(
            onDismissRequest = { onEvent(LaunchesListScreenEvent.BottomSheetDismiss) },
            sheetState = bottomSheetState,
        ) {
            when (uiState.activeBottomSheet) {
                is ActiveBottomSheet.Agency -> MultiSelectFilterBottomSheetContent(
                    title = stringResource(R.string.filter_agency),
                    options = uiState.agencyFilters,
                    initialSelection = uiState.selectedAgencies,
                    onConfirm = { agencies ->
                        scope.launch {
                            bottomSheetState.hide()
                            onEvent(LaunchesListScreenEvent.AgencyFiltersConfirmed(agencies))
                        }
                    },
                )

                is ActiveBottomSheet.Location -> MultiSelectFilterBottomSheetContent(
                    title = stringResource(R.string.filter_location),
                    options = uiState.locationFilters,
                    initialSelection = uiState.selectedLocations,
                    onConfirm = { locations ->
                        scope.launch {
                            bottomSheetState.hide()
                            onEvent(LaunchesListScreenEvent.LocationFiltersConfirmed(locations))
                        }
                    },
                )

                ActiveBottomSheet.None -> Unit
            }
        }
    }
}

@LightAndDarkPreviews
@Composable
fun LaunchesListScreenPreview(
    @PreviewParameter(LaunchListPreviewProvider::class) pagingData: MutableStateFlow<PagingData<LaunchListItemUiModel>>,
) {
    SpacehubTheme {
        LaunchesListScreenContent(
            launchType = LaunchType.UPCOMING,
            launches = pagingData.collectAsLazyPagingItems(),
            uiState = LaunchesListUiState(),
            onEvent = {},
        )
    }
}
