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

package com.lpirro.saved.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lpirro.domain.usecase.GetSavedLaunchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SavedLaunchesViewModel @Inject constructor(
    private val getSavedLaunchesUseCase: GetSavedLaunchesUseCase
) : ViewModel(), SavedLaunchesViewModelContract {

    private val _uiState =
        MutableStateFlow<SavedLaunchesUiState>(SavedLaunchesUiState.Loading(isLoading = true))
    val uiState: StateFlow<SavedLaunchesUiState> = _uiState

    override fun getSavedLaunches(): Job {
        return getSavedLaunchesUseCase()
            .onEach { launches ->
                _uiState.value = if (launches.isEmpty()) SavedLaunchesUiState.NoSavedLaunches
                else SavedLaunchesUiState.Success(launches)
            }
            .catch { _uiState.value = SavedLaunchesUiState.Error }
            .launchIn(viewModelScope)
    }
}
