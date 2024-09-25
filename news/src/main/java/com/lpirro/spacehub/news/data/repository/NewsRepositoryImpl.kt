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

package com.lpirro.spacehub.news.data.repository

import com.lpirro.spacehub.news.data.mapper.ArticleMapper
import com.lpirro.spacehub.news.data.network.NewsService
import com.lpirro.spacehub.news.domain.model.Article
import com.lpirro.spacehub.news.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsRepositoryImpl(
    private val newsService: NewsService,
    private val articleMapper: ArticleMapper,
) : NewsRepository {
    override fun getNews(): Flow<List<Article>> = flow {
        val result = newsService.getArticles().results.map(articleMapper::mapToDomain)
        emit(result)
    }.flowOn(Dispatchers.IO)

    override fun filterNews(filterQuery: String): Flow<List<Article>> = flow {
        val result = newsService.filterArticles(filterQuery).results.map(articleMapper::mapToDomain)
        emit(result)
    }.flowOn(Dispatchers.IO)
}
