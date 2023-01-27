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

package com.lpirro.launch_detail.vehicles.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lpirro.domain.usecase.GetLaunchDetailOverviewUseCase
import com.lpirro.launch_detail.vehicles.mapper.LaunchVehiclesMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LaunchDetailVehiclesViewModel @Inject constructor(
    private val getLaunchDetailOverviewUseCase: GetLaunchDetailOverviewUseCase,
    private val mapper: LaunchVehiclesMapper
) : ViewModel(), LaunchDetailVehiclesViewModelContract {

    private val _uiState =
        MutableStateFlow<LaunchDetailVehiclesUiState>(LaunchDetailVehiclesUiState.Loading)
    val uiState: StateFlow<LaunchDetailVehiclesUiState> = _uiState

    override fun getLaunch(id: String): Job {
        return getLaunchDetailOverviewUseCase(id)
            .onEach { _uiState.value = LaunchDetailVehiclesUiState.Success(mapper.mapToUi(it.rocket)) }
            .catch { _uiState.value = LaunchDetailVehiclesUiState.Error }
            .launchIn(viewModelScope)
    }
}
