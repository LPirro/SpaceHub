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

import com.lpirro.domain.repository.NewsRepository
import com.lpirro.network.NewsApiService
import com.lpirro.persistence.room.ArticleDao
import com.lpirro.repository.mapper.ArticleMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class NewsRepositoryImpl(
    private val newsApiService: NewsApiService,
    private val articleDao: ArticleDao,
    private val articleMapper: ArticleMapper
) : NewsRepository {

    override suspend fun getNews() = flow {
        loadArticles()
        val articles = articleDao.getAll().map(articleMapper::mapToDomain)
        emit(articles)
    }.flowOn(Dispatchers.IO)

    override suspend fun filterNews(filterQuery: String) = flow {
        val articles = newsApiService.filterArticles(filterQuery).map(articleMapper::mapToDomain)
        emit(articles)
    }.flowOn(Dispatchers.IO)

    private suspend fun loadArticles() {
        return withContext(Dispatchers.IO) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if (articleDao.getAll().isEmpty())
                            throw Exception()
                    }
                    else -> throw e
                }
            }
        }
    }

    private suspend fun refreshCache() {
        val remoteArticles = newsApiService.getArticles()
        articleDao.deleteAll()
        articleDao.insertAll(remoteArticles.map(articleMapper::mapToLocal))
    }
}
