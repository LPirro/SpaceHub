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
package com.spacehub.launches.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spacehub.core.result.Result
import com.spacehub.launches.domain.usecase.GetPastLaunchesUseCase
import com.spacehub.launches.domain.usecase.GetUpcomingLaunchesUseCase
import com.spacehub.launches.presentation.mapper.LaunchUiMapper
import com.spacehub.launches.presentation.model.LaunchUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val getUpcomingLaunchesUseCase: GetUpcomingLaunchesUseCase,
    private val getPastLaunchesUseCase: GetPastLaunchesUseCase,
    private val launchUiMapper: LaunchUiMapper,
) : ViewModel() {
    private val _uiState = MutableStateFlow<LaunchesUiState>(LaunchesUiState.Loading(true))
    val uiState = _uiState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = LaunchesUiState.Loading(true),
        )

    private val _isRefreshLoading = MutableStateFlow(false)
    val isRefreshLoading = _isRefreshLoading.asStateFlow()

    init {
        getLaunches()
    }

    fun getLaunches(isRefresh: Boolean = false) = viewModelScope.launch {
        if (!isRefresh) {
            _uiState.value = LaunchesUiState.Loading(true)
        }
        _isRefreshLoading.value = isRefresh

        try {
            // Load both upcoming and past launches in parallel
            val upcomingDeferred = async {
                getUpcomingLaunchesUseCase(forceRefresh = isRefresh).first()
            }
            val pastDeferred = async {
                getPastLaunchesUseCase(forceRefresh = isRefresh).first()
            }

            val upcomingResult = upcomingDeferred.await()
            val pastResult = pastDeferred.await()

            // Only emit Success if both are successful
            _uiState.value = when {
                upcomingResult is Result.Success && pastResult is Result.Success -> {
                    val upcomingLaunches = upcomingResult.data.map { launch ->
                        launchUiMapper.mapToUi(launch)
                    }
                    val pastLaunches = pastResult.data.map { launch ->
                        launchUiMapper.mapToUi(launch)
                    }
                    LaunchesUiState.Success(
                        upcomingLaunches = upcomingLaunches,
                        pastLaunches = pastLaunches
                    )
                }
                else -> LaunchesUiState.Error
            }
        } catch (_: Exception) {
            _uiState.value = LaunchesUiState.Error
        } finally {
            _isRefreshLoading.value = false
        }
    }

    fun refresh() {
        getLaunches(isRefresh = true)
    }
}

sealed class LaunchesUiState {
    data class Loading(val isLoading: Boolean) : LaunchesUiState()

    data class Success(
        val upcomingLaunches: List<LaunchUiModel>,
        val pastLaunches: List<LaunchUiModel>,
    ) : LaunchesUiState()

    data object Error : LaunchesUiState()
}