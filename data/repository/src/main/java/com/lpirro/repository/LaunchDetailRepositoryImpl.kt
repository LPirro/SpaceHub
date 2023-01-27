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

import com.lpirro.domain.repository.LaunchDetailRepository
import com.lpirro.network.LaunchLibraryApiService
import com.lpirro.persistence.model.LaunchType
import com.lpirro.persistence.room.LaunchDao
import com.lpirro.repository.mapper.LaunchMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class LaunchDetailRepositoryImpl(
    private val launchLibraryApiService: LaunchLibraryApiService,
    private val launchDao: LaunchDao,
    private val mapper: LaunchMapper
) : LaunchDetailRepository {

    override fun getLaunch(id: String) = flow {
        val launchLocal = launchDao.getLaunch(id)

        if (launchLocal != null) {
            emit(mapper.mapToDomain(launchLocal))
        } else {
            refreshCache(id)
            val newLaunchLocal = launchDao.getLaunch(id)
            emit(mapper.mapToDomain(newLaunchLocal!!))
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun refreshCache(launchId: String) {
        return withContext(Dispatchers.IO) {
            val launchRemote = launchLibraryApiService.getLaunch(launchId)
            val launchLocal = mapper.mapToLocal(launchRemote, LaunchType.UNKNOWN)
            launchDao.insert(launchLocal)
        }
    }
}
