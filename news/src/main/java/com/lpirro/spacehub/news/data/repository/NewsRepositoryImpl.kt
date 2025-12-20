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

import com.google.gson.JsonParseException
import com.lpirro.spacehub.core.result.DataError
import com.lpirro.spacehub.core.result.Result
import com.lpirro.spacehub.news.data.mapper.ArticleMapper
import com.lpirro.spacehub.news.data.network.NewsService
import com.lpirro.spacehub.news.domain.model.Article
import com.lpirro.spacehub.news.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class NewsRepositoryImpl(
    private val newsService: NewsService,
    private val articleMapper: ArticleMapper,
) : NewsRepository {
    override fun getNews(): Flow<Result<List<Article>>> = flow {
        try {
            val result = newsService.getArticles().results.map(articleMapper::mapToDomain)
            emit(Result.Success(result))
        } catch (e: IOException) {
            emit(Result.Error(DataError.NoInternet))
        } catch (e: HttpException) {
            emit(Result.Error(handleHttpException(e)))
        } catch (e: JsonParseException) {
            emit(Result.Error(DataError.Parse(e.message ?: "Parsing failed")))
        } catch (e: Exception) {
            emit(Result.Error(DataError.Unknown))
        }
    }.flowOn(Dispatchers.IO)

    override fun filterNews(filterQuery: String): Flow<Result<List<Article>>> = flow {
        try {
            val result = newsService.filterArticles(filterQuery).results.map(articleMapper::mapToDomain)
            emit(Result.Success(result))
        } catch (e: IOException) {
            emit(Result.Error(DataError.NoInternet))
        } catch (e: HttpException) {
            emit(Result.Error(handleHttpException(e)))
        } catch (e: JsonParseException) {
            emit(Result.Error(DataError.Parse(e.message ?: "Parsing failed")))
        } catch (e: Exception) {
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
