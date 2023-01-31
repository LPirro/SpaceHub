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

package com.lpirro.network

import com.lpirro.network.models.ArticleRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("articles")
    suspend fun getArticles(@Query("_limit") resultLimit: String = "50"): List<ArticleRemote>

    @GET("articles")
    suspend fun filterArticles(
        @Query("title_contains") title: String,
        @Query("_limit") resultLimit: String = "50"
    ): List<ArticleRemote>
}
