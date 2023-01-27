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
import com.lpirro.persistence.model.SavedLaunchLocal
import com.lpirro.persistence.room.LaunchDao
import com.lpirro.repository.mapper.LaunchMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class SavedLaunchesRepositoryImpl(
    private val launchDao: LaunchDao,
    private val launchMapper: LaunchMapper
) : SavedLaunchesRepository {

    override fun getLaunches() = flow {
        val launches = launchDao.getSavedLaunches().map { it.launchLocal }
        emit(launches.map(launchMapper::mapToDomain))
    }.flowOn(Dispatchers.IO)

    override suspend fun addLaunch(launchId: String) = withContext(Dispatchers.IO) {
        val launch = launchDao.getLaunch(launchId)!!
        launchDao.insertSavedLaunch(SavedLaunchLocal(launchId, launch))
    }

    override suspend fun removeLaunch(launchId: String) = withContext(Dispatchers.IO) {
        launchDao.deleteSavedLaunch(launchId)
    }

    override fun isSaved(launchId: String) = flow {
        val isSaved = launchDao.isSaved(launchId)
        emit(isSaved)
    }.flowOn(Dispatchers.IO)
}
