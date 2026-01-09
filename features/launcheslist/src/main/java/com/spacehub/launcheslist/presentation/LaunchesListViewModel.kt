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
package com.spacehub.launcheslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.spacehub.common.models.domain.LaunchType
import com.spacehub.launcheslist.domain.usecase.GetLaunchesListUseCase
import com.spacehub.launcheslist.presentation.mapper.LaunchListUiMapper
import com.spacehub.launcheslist.presentation.model.LaunchListItemUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@HiltViewModel(assistedFactory = LaunchesListViewModel.Factory::class)
class LaunchesListViewModel @AssistedInject constructor(
    @Assisted val launchType: LaunchType,
    getLaunchesListUseCase: GetLaunchesListUseCase,
    private val launchListUiMapper: LaunchListUiMapper,
) : ViewModel() {

    val launches: Flow<PagingData<LaunchListItemUiModel>> = getLaunchesListUseCase(launchType)
        .map { pagingData -> pagingData.map { launchListUiMapper.mapToUi(it) } }
        .cachedIn(viewModelScope)

    @AssistedFactory
    interface Factory {
        fun create(launchType: LaunchType): LaunchesListViewModel
    }
}