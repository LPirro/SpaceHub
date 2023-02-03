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

package com.lpirro.launches.upcoming.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lpirro.domain.usecase.GetUpcomingLaunchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UpcomingLaunchesViewModel @Inject constructor(
    private val getUpcomingLaunchesUseCase: GetUpcomingLaunchesUseCase
) : ViewModel(), UpcomingLaunchesViewModelContract {

    private val _uiState =
        MutableStateFlow<UpcomingLaunchesUiState>(UpcomingLaunchesUiState.Loading(true))
    val uiState: StateFlow<UpcomingLaunchesUiState> = _uiState

    init {
        getUpcomingLaunches()
    }

    override fun getUpcomingLaunches(): Job {
        return getUpcomingLaunchesUseCase()
            .onEach { launches ->
                _uiState.value = UpcomingLaunchesUiState.Loading(isLoading = false)
                _uiState.value = UpcomingLaunchesUiState.Success(launches)
            }.catch {
                Timber.d(it)
                _uiState.value = UpcomingLaunchesUiState.Loading(isLoading = false)
                _uiState.value = UpcomingLaunchesUiState.Error
            }.launchIn(viewModelScope)
    }

    override fun refresh() {
        _uiState.value = UpcomingLaunchesUiState.Loading(isLoading = true)
        getUpcomingLaunches()
    }
}
