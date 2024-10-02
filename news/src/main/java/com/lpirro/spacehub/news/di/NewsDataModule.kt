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

package com.lpirro.spacehub.news.di

import com.lpirro.spacehub.core.util.DateParser
import com.lpirro.spacehub.news.data.mapper.ArticleMapper
import com.lpirro.spacehub.news.data.mapper.ArticleMapperImpl
import com.lpirro.spacehub.news.data.network.NewsService
import com.lpirro.spacehub.news.data.repository.NewsRepositoryImpl
import com.lpirro.spacehub.news.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NewsDataModule {

    @Provides
    fun provideNewsRepository(
        newsService: NewsService,
        articleMapper: ArticleMapper,
    ): NewsRepository = NewsRepositoryImpl(newsService = newsService, articleMapper = articleMapper)

    @Provides
    fun providesArticleMapper(dateParser: DateParser): ArticleMapper =
        ArticleMapperImpl(dateParser = dateParser)
}
