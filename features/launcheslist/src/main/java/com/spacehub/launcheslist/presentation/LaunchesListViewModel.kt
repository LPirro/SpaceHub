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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.spacehub.common.models.domain.LaunchType
import com.spacehub.launcheslist.domain.usecase.GetLaunchFiltersUseCase
import com.spacehub.launcheslist.domain.usecase.GetLaunchesListUseCase
import com.spacehub.launcheslist.presentation.mapper.LaunchListUiMapper
import com.spacehub.launcheslist.presentation.model.LaunchListItemUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel(assistedFactory = LaunchesListViewModel.Factory::class)
class LaunchesListViewModel @AssistedInject constructor(
    @Assisted val launchType: LaunchType,
    private val getLaunchesListUseCase: GetLaunchesListUseCase,
    getLaunchFiltersUseCase: GetLaunchFiltersUseCase,
    private val launchListUiMapper: LaunchListUiMapper,
) : ViewModel() {

    private val filters = getLaunchFiltersUseCase()

    private val _uiState = MutableStateFlow(
        LaunchesListUiState(
            agencyFilters = filters.agencies,
            locationFilters = filters.locations,
        )
    )
    val uiState: StateFlow<LaunchesListUiState> = _uiState.asStateFlow()

    private val _effect = Channel<LaunchesListScreenEffect>()
    val effect: Flow<LaunchesListScreenEffect> = _effect.receiveAsFlow()

    val launches: Flow<PagingData<LaunchListItemUiModel>> = _uiState
        .map { it.selectedAgencies to it.selectedLocations }
        .distinctUntilChanged()
        .flatMapLatest { (agencies, locations) ->
            getLaunchesListUseCase(
                launchType = launchType,
                agencyFilter = agencies.map { it.id },
                locationFilter = locations.map { it.id },
            ).map { pagingData ->
                pagingData.map { launchListUiMapper.mapToUi(it) }
            }
        }
        .cachedIn(viewModelScope)

    fun onEvent(event: LaunchesListScreenEvent) {
        when (event) {
            is LaunchesListScreenEvent.AgencyFilterClick -> {
                _uiState.value = _uiState.value.copy(activeBottomSheet = ActiveBottomSheet.Agency)
            }

            is LaunchesListScreenEvent.LocationFilterClick -> {
                _uiState.value = _uiState.value.copy(activeBottomSheet = ActiveBottomSheet.Location)
            }

            is LaunchesListScreenEvent.BottomSheetDismiss -> {
                _uiState.value = _uiState.value.copy(activeBottomSheet = ActiveBottomSheet.None)
            }

            is LaunchesListScreenEvent.AgencyFiltersConfirmed -> {
                _uiState.value = _uiState.value.copy(
                    selectedAgencies = event.agencies,
                    activeBottomSheet = ActiveBottomSheet.None,
                )
            }

            is LaunchesListScreenEvent.LocationFiltersConfirmed -> {
                _uiState.value = _uiState.value.copy(
                    selectedLocations = event.locations,
                    activeBottomSheet = ActiveBottomSheet.None,
                )
            }

            is LaunchesListScreenEvent.LaunchClick -> {
                viewModelScope.launch {
                    _effect.send(LaunchesListScreenEffect.NavigateToLaunchDetail(event.id, event.name))
                }
            }

            is LaunchesListScreenEvent.BackClick -> {
                viewModelScope.launch {
                    _effect.send(LaunchesListScreenEffect.NavigateBack)
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(launchType: LaunchType): LaunchesListViewModel
    }
}