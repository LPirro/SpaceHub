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

import com.google.gson.JsonParseException
import com.spacehub.common.data.network.LaunchesService
import com.spacehub.common.domain.repository.LaunchesRepository
import com.spacehub.common.domain.repository.PagedLaunches
import com.spacehub.common.mapper.LaunchMapper
import com.spacehub.common.models.remote.LaunchRemote
import com.spacehub.core.result.DataError
import com.spacehub.core.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class LaunchesRepositoryImpl(
    private val launchesService: LaunchesService,
    private val launchMapper: LaunchMapper,
) : LaunchesRepository {

    private val cacheUpcomingLaunches = MutableStateFlow<List<LaunchRemote>?>(null)
    private val cachePastLaunchesLaunches = MutableStateFlow<List<LaunchRemote>?>(null)

    override fun getUpcomingLaunches(forceRefresh: Boolean, limit: Int, offset: Int) = flow {
        try {
            val response = if (!forceRefresh && offset == 0 && cacheUpcomingLaunches.value != null) {
                val cached = cacheUpcomingLaunches.value!!
                emit(Result.Success(PagedLaunches(
                    launches = cached.map { launchMapper.mapToDomain(it) },
                    hasNextPage = cached.size >= limit,
                )))
                return@flow
            } else {
                launchesService.getUpcomingLaunches(limit = limit, offset = offset).also {
                    if (offset == 0) cacheUpcomingLaunches.value = it.results
                }
            }
            emit(Result.Success(PagedLaunches(
                launches = response.results.map { launchMapper.mapToDomain(it) },
                hasNextPage = response.next != null,
            )))
        } catch (_: IOException) {
            emit(Result.Error(DataError.NoInternet))
        } catch (e: HttpException) {
            emit(Result.Error(handleHttpException(e)))
        } catch (e: JsonParseException) {
            emit(Result.Error(DataError.Parse(e.message ?: "Parsing failed")))
        } catch (_: Exception) {
            emit(Result.Error(DataError.Unknown))
        }
    }.flowOn(Dispatchers.IO)

    override fun getPastLaunches(forceRefresh: Boolean, limit: Int, offset: Int) = flow {
        try {
            val response = if (!forceRefresh && offset == 0 && cachePastLaunchesLaunches.value != null) {
                val cached = cachePastLaunchesLaunches.value!!
                emit(Result.Success(PagedLaunches(
                    launches = cached.map { launchMapper.mapToDomain(it) },
                    hasNextPage = cached.size >= limit,
                )))
                return@flow
            } else {
                launchesService.getPastLaunches(limit = limit, offset = offset).also {
                    if (offset == 0) cachePastLaunchesLaunches.value = it.results
                }
            }
            emit(Result.Success(PagedLaunches(
                launches = response.results.map { launchMapper.mapToDomain(it) },
                hasNextPage = response.next != null,
            )))
        } catch (_: IOException) {
            emit(Result.Error(DataError.NoInternet))
        } catch (e: HttpException) {
            emit(Result.Error(handleHttpException(e)))
        } catch (e: JsonParseException) {
            emit(Result.Error(DataError.Parse(e.message ?: "Parsing failed")))
        } catch (_: Exception) {
            emit(Result.Error(DataError.Unknown))
        }
    }.flowOn(Dispatchers.IO)

    override fun getLaunch(id: String) = flow {
        try {
            val cachedLaunch = cacheUpcomingLaunches.value?.find { it.id == id }
                ?: cachePastLaunchesLaunches.value?.find { it.id == id }

            val launch = cachedLaunch ?: launchesService.getLaunch(id)
            emit(Result.Success(launchMapper.mapToDomain(launch)))
        } catch (_: IOException) {
            emit(Result.Error(DataError.NoInternet))
        } catch (e: HttpException) {
            emit(Result.Error(handleHttpException(e)))
        } catch (e: JsonParseException) {
            emit(Result.Error(DataError.Parse(e.message ?: "Parsing failed")))
        } catch (_: Exception) {
            emit(Result.Error(DataError.Unknown))
        }
    }.flowOn(Dispatchers.IO)

    private fun handleHttpException(e: HttpException): DataError = when (e.code()) {
        401 -> DataError.Unauthorized
        404 -> DataError.NotFound
        in 500..599 -> DataError.ServerError
        else -> DataError.Network(e.code(), e.message())
    }
}
