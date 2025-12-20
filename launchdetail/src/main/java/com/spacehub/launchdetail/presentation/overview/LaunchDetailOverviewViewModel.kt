/*
 *
 *  * SpaceHub - Designed and Developed by LPirro (Leonardo Pirro)
 *  * Copyright (C) 2023 Leonardo Pirro
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.spacehub.launchdetail.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lpirro.spacehub.core.result.DataError
import com.lpirro.spacehub.core.result.Result
import com.lpirro.spacehub.core.result.toUserMessage
import com.lpirro.spacehub.core.util.flow.UiEvent
import com.spacehub.launchdetail.domain.usecase.GetLaunchUseCase
import com.spacehub.launchdetail.presentation.overview.mapper.LaunchDetailOverviewUiMapper
import com.spacehub.launchdetail.presentation.overview.model.LaunchOverviewUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = LaunchDetailOverviewViewModel.Factory::class)
class LaunchDetailOverviewViewModel @AssistedInject constructor(
    @Assisted val launchId: String,
    private val getLaunchUseCase: GetLaunchUseCase,
    private val mapper: LaunchDetailOverviewUiMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LaunchDetailOverviewUiState(isLoading = true))
    val uiState = _uiState
        .onStart { getLaunch(launchId) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = LaunchDetailOverviewUiState(isLoading = true),
        )

    private val _events = UiEvent<LaunchDetailOverviewEvent>()
    val events: Flow<LaunchDetailOverviewEvent> = _events

    private fun getLaunch(id: String) = viewModelScope.launch {
        getLaunchUseCase(id)
            .collectLatest { result ->
                _uiState.value = when (result) {
                    is Result.Success -> {
                        LaunchDetailOverviewUiState(
                            launchOverviewUi = mapper.mapToUi(result.data),
                            isLoading = false,
                            error = false,
                        )
                    }

                    is Result.Error -> {
                        LaunchDetailOverviewUiState(
                            error = true,
                        )
                    }
                }
            }
    }

    fun openGoogleMaps(url: String) = viewModelScope.launch {
        _events.emit(LaunchDetailOverviewEvent.OpenGoogleMaps(url))
    }

    fun openChromeCustomTab(url: String) = viewModelScope.launch {
        _events.emit(LaunchDetailOverviewEvent.OpenChromeCustomTab(url))
    }

    fun addLaunchToCalendar(launchName: String, launchDateMillis: Long) = viewModelScope.launch {
        _events.emit(LaunchDetailOverviewEvent.AddToCalendar(launchName, launchDateMillis))
    }

    data class LaunchDetailOverviewUiState(
        val launchOverviewUi: LaunchOverviewUi? = null,
        val isLoading: Boolean = false,
        val error: Boolean = false,
    )

    sealed class LaunchDetailOverviewEvent {
        data class OpenLaunchTrajectory(val url: String) : LaunchDetailOverviewEvent()
        data class OpenChromeCustomTab(val url: String) : LaunchDetailOverviewEvent()
        data class OpenGoogleMaps(val url: String) : LaunchDetailOverviewEvent()
        data class AddToCalendar(
            val launchName: String,
            val launchDateMillis: Long,
        ) : LaunchDetailOverviewEvent()
    }

    @AssistedFactory
    interface Factory {
        fun create(launchId: String): LaunchDetailOverviewViewModel
    }
}
