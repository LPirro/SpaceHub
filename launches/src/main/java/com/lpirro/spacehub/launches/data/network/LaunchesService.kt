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

package com.lpirro.spacehub.launches.data.network

import com.lpirro.spacehub.launches.data.network.model.LaunchRemote
import com.lpirro.spacehub.launches.data.network.model.PaginatedResultRemote
import retrofit2.http.GET
import retrofit2.http.Path

interface LaunchesService {

    // TODO: Use @Query instead of hardcoding params
    @GET("launch/upcoming?mode=detailed&limit=20&hide_recent_previous=true")
    suspend fun getUpcomingLaunches(): PaginatedResultRemote<List<LaunchRemote>>

    // TODO: Use @Query instead of hardcoding params
    @GET("launch/previous?mode=detailed&limit=20")
    suspend fun getPastLaunches(): PaginatedResultRemote<List<LaunchRemote>>

    @GET("launch/{id}")
    suspend fun getLaunch(@Path("id") id: String): LaunchRemote
}
