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
package com.spacehub.common.data.network

import com.spacehub.common.models.remote.LaunchRemote
import com.spacehub.common.models.remote.PaginatedResultRemote
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LaunchesService {
    @GET("launch/upcoming")
    suspend fun getUpcomingLaunches(
        @Query("mode") mode: String = "detailed",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("lsp__ids") agencyIds: String? = null,
        @Query("hide_recent_previous") hideRecentPrevious: Boolean = true,
    ): PaginatedResultRemote<List<LaunchRemote>>

    @GET("launch/previous")
    suspend fun getPastLaunches(
        @Query("mode") mode: String = "detailed",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("lsp__ids") agencyIds: String? = null,
    ): PaginatedResultRemote<List<LaunchRemote>>

    @GET("launch/{id}")
    suspend fun getLaunch(
        @Path("id") id: String,
    ): LaunchRemote
}
