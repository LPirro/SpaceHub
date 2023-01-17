/*
 *
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
 *
 */

package com.lpirro.launches.upcoming.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lpirro.domain.usecase.GetUpcomingLaunchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingLaunchesViewModel @Inject constructor(
    private val getUpcomingLaunchesUseCase: GetUpcomingLaunchesUseCase
) : ViewModel(), UpcomingLaunchesViewModelContract {

    private val _uiState =
        MutableStateFlow<UpcomingLaunchesUiState>(UpcomingLaunchesUiState.Loading)
    val uiState: StateFlow<UpcomingLaunchesUiState> = _uiState

    init {
        getUpcomingLaunches()
    }

    override fun getUpcomingLaunches() = viewModelScope.launch {
        try {
            getUpcomingLaunchesUseCase().collect { launches ->
                _uiState.value = UpcomingLaunchesUiState.Refresh(isRefreshing = false)
                _uiState.value = UpcomingLaunchesUiState.Success(launches)
            }
        } catch (e: Exception) {
            _uiState.value = UpcomingLaunchesUiState.Refresh(isRefreshing = false)
            _uiState.value = UpcomingLaunchesUiState.Error
        }
    }

    override fun refresh() {
        _uiState.value = UpcomingLaunchesUiState.Refresh(isRefreshing = true)
        getUpcomingLaunches()
    }
}
