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

package com.lpirro.repository

import com.lpirro.domain.repository.LaunchesRepository
import com.lpirro.network.LaunchLibraryApiService
import com.lpirro.network.models.LaunchRemote
import com.lpirro.network.models.PaginatedResultRemote
import com.lpirro.persistence.model.LaunchType
import com.lpirro.persistence.room.LaunchDao
import com.lpirro.repository.mapper.LaunchMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class LaunchesRepositoryImpl(
    private val spaceHubApiService: LaunchLibraryApiService,
    private val launchDao: LaunchDao,
    private val mapper: LaunchMapper
) : LaunchesRepository {

    override suspend fun getUpcomingLaunches() = flow {
        refreshCache({ spaceHubApiService.getUpcomingLaunches() }, LaunchType.UPCOMING)
        val launches = launchDao.getLaunchesWithType(LaunchType.UPCOMING).map(mapper::mapToDomain)
        emit(launches)
    }.flowOn(Dispatchers.IO)

    override suspend fun getPastLaunches() = flow {
        refreshCache({ spaceHubApiService.getPastLaunches() }, LaunchType.PAST)
        val launches = launchDao.getLaunchesWithType(LaunchType.PAST).map(mapper::mapToDomain)
        emit(launches)
    }.flowOn(Dispatchers.IO)

    private suspend fun refreshCache(
        networkCall: suspend () -> PaginatedResultRemote<List<LaunchRemote>>,
        type: LaunchType
    ) {
        return withContext(Dispatchers.IO) {
            try {
                val result = networkCall.invoke().results.map { mapper.mapToLocal(it, type) }
                launchDao.deleteOldLaunches(result.map { it.id }, type)
                launchDao.insertAll(result)
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if (launchDao.getLaunchesWithType(type).isEmpty())
                            throw Exception()
                    }
                    else -> throw e
                }
            }
        }
    }
}
