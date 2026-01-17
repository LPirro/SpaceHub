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
package com.spacehub.launcheslist.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.spacehub.common.data.paging.LaunchesPagingSource
import com.spacehub.common.domain.repository.LaunchesRepository
import com.spacehub.common.models.domain.Launch
import com.spacehub.common.models.domain.LaunchType
import kotlinx.coroutines.flow.Flow

interface GetLaunchesListUseCase {
    operator fun invoke(
        launchType: LaunchType,
        agencyFilter: List<String> = emptyList(),
        locationFilter: List<String> = emptyList(),
    ): Flow<PagingData<Launch>>
}

class GetLaunchesListUseCaseImpl(
    private val launchesRepository: LaunchesRepository,
) : GetLaunchesListUseCase {

    override fun invoke(
        launchType: LaunchType,
        agencyFilter: List<String>,
        locationFilter: List<String>,
    ): Flow<PagingData<Launch>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = {
            LaunchesPagingSource(
                launchesRepository = launchesRepository,
                launchType = launchType,
                agencyFilter = agencyFilter,
                locationFilter = locationFilter,
            )
        },
    ).flow

    companion object {
        private const val PAGE_SIZE = 20
    }
}
