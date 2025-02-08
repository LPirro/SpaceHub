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
import com.spacehub.launchdetail.domain.usecase.GetLaunchUseCase
import com.spacehub.launchdetail.presentation.overview.mapper.LaunchDetailOverviewUiMapper
import com.spacehub.launchdetail.presentation.overview.model.LaunchOverviewUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
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

    private fun getLaunch(id: String) = viewModelScope.launch {
        getLaunchUseCase(id)
            .catch { _uiState.value = LaunchDetailOverviewUiState(error = true) }
            .collectLatest {
                _uiState.value = LaunchDetailOverviewUiState(
                    launchOverviewUi = mapper.mapToUi(it),
                    isLoading = false,
                    error = false,
                )
            }
    }

    data class LaunchDetailOverviewUiState(
        val launchOverviewUi: LaunchOverviewUi? = null,
        val isLoading: Boolean = false,
        val error: Boolean = false,
    )

    @AssistedFactory
    interface Factory {
        fun create(launchId: String): LaunchDetailOverviewViewModel
    }
}
