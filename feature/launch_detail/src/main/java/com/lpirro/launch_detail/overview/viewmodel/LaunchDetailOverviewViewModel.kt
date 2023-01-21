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

package com.lpirro.launch_detail.overview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lpirro.domain.usecase.GetLaunchDetailOverviewUseCase
import com.lpirro.launch_detail.overview.mapper.LaunchOverviewMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LaunchDetailOverviewViewModel @Inject constructor(
    private val getLaunchDetailOverviewUseCase: GetLaunchDetailOverviewUseCase,
    private val mapper: LaunchOverviewMapper
) : ViewModel(), LaunchDetailOverviewViewModelContract {

    private val _uiState =
        MutableStateFlow<LaunchDetailOverviewUiState>(LaunchDetailOverviewUiState.Loading)
    val uiState: StateFlow<LaunchDetailOverviewUiState> = _uiState

    private val _events = MutableSharedFlow<LaunchDetailOverviewEvent>()
    val events: SharedFlow<LaunchDetailOverviewEvent> = _events

    override fun getLaunch(id: String) = viewModelScope.launch {
        try {
            getLaunchDetailOverviewUseCase(id).collect { launch ->
                _uiState.value = LaunchDetailOverviewUiState.Success(mapper.mapToUi(launch))
            }
        } catch (e: java.lang.Exception) {
            _uiState.value = LaunchDetailOverviewUiState.Error
            Timber.d(e)
        }
    }

    override fun openLaunchTrajectory(url: String) {
        viewModelScope.launch {
            _events.emit(LaunchDetailOverviewEvent.OpenLaunchTrajectory(url))
        }
    }

    override fun openGoogleMaps(url: String) {
        viewModelScope.launch {
            _events.emit(LaunchDetailOverviewEvent.OpenGoogleMaps(url))
        }
    }

    override fun openChromeCustomTab(url: String) {
        viewModelScope.launch {
            _events.emit(LaunchDetailOverviewEvent.OpenChromeCustomTab(url))
        }
    }

    override fun openYouTubeInFullScreen(videoId: String) {
        viewModelScope.launch {
            _events.emit(LaunchDetailOverviewEvent.OpenYouTubeInFullScreen(videoId))
        }
    }

    override fun addLaunchToCalendar(launchName: String, launchDateMillis: Long) {
        viewModelScope.launch {
            _events.emit(LaunchDetailOverviewEvent.AddToCalendar(launchName, launchDateMillis))
        }
    }
}
