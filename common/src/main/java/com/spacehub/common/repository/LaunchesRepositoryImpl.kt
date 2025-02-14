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
package com.spacehub.common.repository

import com.spacehub.common.data.network.LaunchesService
import com.spacehub.common.domain.repository.LaunchesRepository
import com.spacehub.common.mapper.LaunchMapper
import com.spacehub.common.models.remote.LaunchRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LaunchesRepositoryImpl(
    private val launchesService: LaunchesService,
    private val launchMapper: LaunchMapper,
) : LaunchesRepository {

    private val cacheUpcomingLaunches = MutableStateFlow<List<LaunchRemote>?>(null)
    private val cachePastLaunchesLaunches = MutableStateFlow<List<LaunchRemote>?>(null)

    override fun getUpcomingLaunches(forceRefresh: Boolean) = flow {
        val launches = if (!forceRefresh && cacheUpcomingLaunches.value != null) {
            cacheUpcomingLaunches.value!!
        } else {
            launchesService.getUpcomingLaunches().results.also {
                cacheUpcomingLaunches.value = it
            }
        }
        emit(launches.map { launchMapper.mapToDomain(it) })
    }.flowOn(Dispatchers.IO)

    override fun getPastLaunches(forceRefresh: Boolean) = flow {
        val launches = if (!forceRefresh && cachePastLaunchesLaunches.value != null) {
            cachePastLaunchesLaunches.value!!
        } else {
            launchesService.getPastLaunches().results.also {
                cachePastLaunchesLaunches.value = it
            }
        }
        emit(launches.map { launchMapper.mapToDomain(it) })
    }.flowOn(Dispatchers.IO)

    override fun getLaunch(id: String) = flow {
        val cachedLaunch = cacheUpcomingLaunches.value?.find { it.id == id }
            ?: cachePastLaunchesLaunches.value?.find { it.id == id }

        if (cachedLaunch != null) {
            emit(launchMapper.mapToDomain(cachedLaunch))
        } else {
            val launch = launchesService.getLaunch(id)
            emit(launchMapper.mapToDomain(launch))
        }
    }.flowOn(Dispatchers.IO)
}
