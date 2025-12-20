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
package com.lpirro.spacehub.launches.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lpirro.spacehub.core.result.Result
import com.lpirro.spacehub.launches.domain.usecase.GetPastLaunchesUseCase
import com.lpirro.spacehub.launches.domain.usecase.GetUpcomingLaunchesUseCase
import com.lpirro.spacehub.launches.presentation.mapper.LaunchUiMapper
import com.lpirro.spacehub.launches.presentation.model.LaunchUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val getUpcomingLaunchesUseCase: GetUpcomingLaunchesUseCase,
    private val getPastLaunchesUseCase: GetPastLaunchesUseCase,
    private val launchUiMapper: LaunchUiMapper,
) : ViewModel() {
    private val _uiStateUpcomingLaunches =
        MutableStateFlow<LaunchesUiState>(LaunchesUiState.Loading(true))
    val uiStateUpcomingLaunches =
        _uiStateUpcomingLaunches
            .onStart { getUpcomingLaunches() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = LaunchesUiState.Loading(true),
            )

    private val _uiStatePastLaunches =
        MutableStateFlow<LaunchesUiState>(LaunchesUiState.Loading(true))
    val uiStatePastLaunches =
        _uiStatePastLaunches
            .onStart { getPastLaunches() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = LaunchesUiState.Loading(true),
            )

    private val _isRefreshLoading = MutableStateFlow(false)
    val isRefreshLoading = _isRefreshLoading.asStateFlow()

    fun getUpcomingLaunches(isRefresh: Boolean = false) =
        viewModelScope.launch {
            if (!isRefresh) {
                _uiStateUpcomingLaunches.value = LaunchesUiState.Loading(true)
            }
            _isRefreshLoading.value = isRefresh

            getUpcomingLaunchesUseCase(forceRefresh = isRefresh)
                .onCompletion { _isRefreshLoading.value = false }
                .collect { result ->
                    _uiStateUpcomingLaunches.value = when (result) {
                        is Result.Success -> {
                            val launches = result.data.map { launch -> launchUiMapper.mapToUi(launch) }
                            LaunchesUiState.Success(launches)
                        }

                        is Result.Error -> {
                            LaunchesUiState.Error
                        }
                    }
                }
        }

    fun getPastLaunches(isRefresh: Boolean = false) =
        viewModelScope.launch {
            if (!isRefresh) {
                _uiStatePastLaunches.value = LaunchesUiState.Loading(false)
            }
            _isRefreshLoading.value = isRefresh

            getPastLaunchesUseCase(forceRefresh = isRefresh)
                .onCompletion { _isRefreshLoading.value = false }
                .collect { result ->
                    _uiStatePastLaunches.value = when (result) {
                        is Result.Success -> {
                            val launches = result.data.map { launch -> launchUiMapper.mapToUi(launch) }
                            LaunchesUiState.Success(launches)
                        }

                        is Result.Error -> {
                            LaunchesUiState.Error
                        }
                    }
                }
        }
}

sealed class LaunchesUiState {
    data class Loading(val isLoading: Boolean) : LaunchesUiState()

    data class Success(val launches: List<LaunchUi>) : LaunchesUiState()

    data object Error : LaunchesUiState()
}
