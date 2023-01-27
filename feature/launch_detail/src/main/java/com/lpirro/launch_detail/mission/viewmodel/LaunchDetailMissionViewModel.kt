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

package com.lpirro.launch_detail.mission.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lpirro.domain.usecase.GetLaunchDetailOverviewUseCase
import com.lpirro.launch_detail.mission.mapper.LaunchMissionMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LaunchDetailMissionViewModel @Inject constructor(
    private val getLaunchDetailOverviewUseCase: GetLaunchDetailOverviewUseCase,
    private val mapper: LaunchMissionMapper
) : ViewModel(), LaunchDetailMissionViewModelContract {

    private val _uiState =
        MutableStateFlow<LaunchDetailMissionUiState>(LaunchDetailMissionUiState.Loading)
    val uiState: StateFlow<LaunchDetailMissionUiState> = _uiState

    override fun getLaunch(id: String): Job {
        return getLaunchDetailOverviewUseCase(id)
            .onEach { _uiState.value = LaunchDetailMissionUiState.Success(mapper.mapToUi(it)) }
            .catch { _uiState.value = LaunchDetailMissionUiState.Error }
            .launchIn(viewModelScope)
    }
}
