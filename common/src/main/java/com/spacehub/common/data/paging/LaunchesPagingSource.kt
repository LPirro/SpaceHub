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
package com.spacehub.common.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.spacehub.common.domain.repository.LaunchesRepository
import com.spacehub.common.models.domain.Launch
import com.spacehub.common.models.domain.LaunchType
import com.spacehub.core.common.result.Result
import kotlinx.coroutines.flow.first

class LaunchesPagingSource(
    private val launchesRepository: LaunchesRepository,
    private val launchType: LaunchType,
    private val agencyFilter: List<String> = emptyList(),
    private val locationFilter: List<String> = emptyList(),
) : PagingSource<Int, Launch>() {

    override fun getRefreshKey(state: PagingState<Int, Launch>): Int? = state.anchorPosition?.let { anchorPosition ->
        state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Launch> {
        val page = params.key ?: 0
        val offset = page * params.loadSize

        return try {
            val result = when (launchType) {
                LaunchType.UPCOMING -> launchesRepository.getUpcomingLaunches(
                    forceRefresh = page > 0,
                    limit = params.loadSize,
                    offset = offset,
                    agencyFilter = agencyFilter,
                    locationFilter = locationFilter,
                ).first()

                LaunchType.PAST -> launchesRepository.getPastLaunches(
                    forceRefresh = page > 0,
                    limit = params.loadSize,
                    offset = offset,
                    agencyFilter = agencyFilter,
                    locationFilter = locationFilter,
                ).first()
            }

            when (result) {
                is Result.Success -> LoadResult.Page(
                    data = result.data.launches,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (result.data.hasNextPage) page + 1 else null,
                )

                is Result.Error -> LoadResult.Error(Exception("Failed to load launches"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
