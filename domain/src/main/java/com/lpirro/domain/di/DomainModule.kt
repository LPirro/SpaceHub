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

package com.lpirro.domain.di

import com.lpirro.domain.repository.LaunchDetailRepository
import com.lpirro.domain.repository.LaunchesRepository
import com.lpirro.domain.repository.NewsRepository
import com.lpirro.domain.repository.SavedLaunchesRepository
import com.lpirro.domain.usecase.AddToSavedLaunchesUseCase
import com.lpirro.domain.usecase.AddToSavedLaunchesUseCaseImpl
import com.lpirro.domain.usecase.FilterArticlesUseCase
import com.lpirro.domain.usecase.FilterArticlesUseCaseImpl
import com.lpirro.domain.usecase.GetArticlesUseCase
import com.lpirro.domain.usecase.GetArticlesUseCaseImpl
import com.lpirro.domain.usecase.GetLaunchDetailOverviewUseCase
import com.lpirro.domain.usecase.GetLaunchDetailOverviewUseCaseImpl
import com.lpirro.domain.usecase.GetLaunchDetailUseCase
import com.lpirro.domain.usecase.GetLaunchDetailUseCaseImpl
import com.lpirro.domain.usecase.GetPastLaunchesUseCase
import com.lpirro.domain.usecase.GetPastLaunchesUseCaseImpl
import com.lpirro.domain.usecase.GetSavedLaunchesUseCase
import com.lpirro.domain.usecase.GetSavedLaunchesUseCaseImpl
import com.lpirro.domain.usecase.GetUpcomingLaunchesUseCase
import com.lpirro.domain.usecase.GetUpcomingLaunchesUseCaseImpl
import com.lpirro.domain.usecase.IsOnSavedLaunchesUseCase
import com.lpirro.domain.usecase.IsOnSavedLaunchesUseCaseImpl
import com.lpirro.domain.usecase.RemoveFromSavedLaunchesUseCase
import com.lpirro.domain.usecase.RemoveFromSavedLaunchesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetUpcomingLaunchesUseCase(repository: LaunchesRepository): GetUpcomingLaunchesUseCase {
        return GetUpcomingLaunchesUseCaseImpl(repository)
    }

    @Provides
    fun provideGetPastLaunchesUseCase(repository: LaunchesRepository): GetPastLaunchesUseCase {
        return GetPastLaunchesUseCaseImpl(repository)
    }

    @Provides
    fun provideGetLaunchDetailUseCase(repository: LaunchDetailRepository): GetLaunchDetailUseCase {
        return GetLaunchDetailUseCaseImpl(repository)
    }

    @Provides
    fun provideGetSavedLaunchesUseCase(repository: SavedLaunchesRepository): GetSavedLaunchesUseCase {
        return GetSavedLaunchesUseCaseImpl(repository)
    }

    @Provides
    fun providesGetLaunchDetailOverviewUseCase(repository: LaunchDetailRepository): GetLaunchDetailOverviewUseCase {
        return GetLaunchDetailOverviewUseCaseImpl(repository)
    }

    @Provides
    fun provideGetArticlesUseCase(repository: NewsRepository): GetArticlesUseCase {
        return GetArticlesUseCaseImpl(repository)
    }

    @Provides
    fun provideFilterArticlesUseCase(repository: NewsRepository): FilterArticlesUseCase {
        return FilterArticlesUseCaseImpl(repository)
    }

    @Provides
    fun provideAddToSavedLaunchesUseCase(repository: SavedLaunchesRepository): AddToSavedLaunchesUseCase {
        return AddToSavedLaunchesUseCaseImpl(repository)
    }

    @Provides
    fun provideIsOnSavedLaunchesUseCase(repository: SavedLaunchesRepository): IsOnSavedLaunchesUseCase {
        return IsOnSavedLaunchesUseCaseImpl(repository)
    }

    @Provides
    fun provideRemoveFromSavedLaunchesUseCase(repository: SavedLaunchesRepository): RemoveFromSavedLaunchesUseCase {
        return RemoveFromSavedLaunchesUseCaseImpl(repository)
    }
}
