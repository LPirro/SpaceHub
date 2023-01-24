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

import com.lpirro.domain.repository.SavedLaunchesRepository
import com.lpirro.persistence.model.LaunchType
import com.lpirro.persistence.room.LaunchDao
import com.lpirro.repository.mapper.LaunchMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SavedLaunchesRepositoryImpl(
    private val launchDao: LaunchDao,
    private val launchMapper: LaunchMapper
) : SavedLaunchesRepository {

    override suspend fun getSavedLaunches() = flow {
        val launches = launchDao.getLaunchesWithType(LaunchType.UPCOMING)
        emit(launches.map(launchMapper::mapToDomain))
    }.flowOn(Dispatchers.IO)
}
