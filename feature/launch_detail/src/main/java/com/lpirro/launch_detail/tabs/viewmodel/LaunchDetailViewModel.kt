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

package com.lpirro.launch_detail.tabs.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lpirro.domain.usecase.GetLaunchDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchDetailViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val getLaunchDetailUseCase: GetLaunchDetailUseCase
) : ViewModel(), LaunchDetailViewModelContract {

    private val _uiState =
        MutableStateFlow<LaunchDetailUiState>(LaunchDetailUiState.Loading)
    val uiState: StateFlow<LaunchDetailUiState> = _uiState

    override fun getLaunch(id: String) = viewModelScope.launch {
        try {
            getLaunchDetailUseCase(id).collect { launch ->
                _uiState.value = LaunchDetailUiState.Success(launch)
            }
        } catch (e: java.lang.Exception) {
            _uiState.value = LaunchDetailUiState.Error
        }
    }

    override fun retry() {
        TODO("Not yet implemented")
    }
}
