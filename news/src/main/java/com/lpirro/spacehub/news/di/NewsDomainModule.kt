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

import com.lpirro.spacehub.news.domain.repository.NewsRepository
import com.lpirro.spacehub.news.domain.usecase.FilterNewsUseCase
import com.lpirro.spacehub.news.domain.usecase.FilterNewsUseCaseImpl
import com.lpirro.spacehub.news.domain.usecase.GetNewsUseCase
import com.lpirro.spacehub.news.domain.usecase.GetNewsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NewsDomainModule {

    @Provides
    fun provideGetNewsUseCase(newsRepository: NewsRepository): GetNewsUseCase =
        GetNewsUseCaseImpl(newsRepository = newsRepository)

    @Provides
    fun provideFilterNewsUseCase(newsRepository: NewsRepository): FilterNewsUseCase =
        FilterNewsUseCaseImpl(newsRepository = newsRepository)
}
