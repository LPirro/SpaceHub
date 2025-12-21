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

package com.spacehub.launchdetail.presentation.mission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lpirro.spacehub.core.result.Result
import com.lpirro.spacehub.core.util.flow.UiEvent
import com.spacehub.launchdetail.domain.usecase.GetLaunchUseCase
import com.spacehub.launchdetail.presentation.mission.mapper.LaunchDetailMissionUiMapper
import com.spacehub.launchdetail.presentation.mission.model.LaunchDetailMissionUi
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

@HiltViewModel(assistedFactory = LaunchDetailMissionViewModel.Factory::class)
class LaunchDetailMissionViewModel @AssistedInject constructor(
    @Assisted val launchId: String,
    private val getLaunchUseCase: GetLaunchUseCase,
    private val mapper: LaunchDetailMissionUiMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LaunchDetailMissionUiState(isLoading = true))
    val uiState = _uiState
        .onStart { getLaunch(launchId) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = LaunchDetailMissionUiState(isLoading = true),
        )

    private val _events = UiEvent<LaunchDetailMissionEvent>()
    val events: Flow<LaunchDetailMissionEvent> = _events

    private fun getLaunch(id: String) = viewModelScope.launch {
        getLaunchUseCase(id)
            .collectLatest { result ->
                _uiState.value = when (result) {
                    is Result.Success -> {
                        LaunchDetailMissionUiState(
                            launchMissionUi = mapper.mapToUi(result.data),
                            isLoading = false,
                            error = false,
                        )
                    }

                    is Result.Error -> {
                        LaunchDetailMissionUiState(
                            error = true,
                        )
                    }
                }
            }
    }

    fun onMoreInfoClicked(moreInfoUrl: String) = viewModelScope.launch {
        _events.emit(LaunchDetailMissionEvent.OpenDescriptionMoreInfo(moreInfoUrl))
    }

    data class LaunchDetailMissionUiState(
        val launchMissionUi: LaunchDetailMissionUi? = null,
        val isLoading: Boolean = false,
        val error: Boolean = false,
    )

    sealed class LaunchDetailMissionEvent {
        data class OpenDescriptionMoreInfo(val url: String) : LaunchDetailMissionEvent()
    }

    @AssistedFactory
    interface Factory {
        fun create(launchId: String): LaunchDetailMissionViewModel
    }
}
