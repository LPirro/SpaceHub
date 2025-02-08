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
package com.spacehub.launchdetail.di

import com.spacehub.common.mapper.LaunchMapper
import com.spacehub.launchdetail.data.network.LaunchDetailService
import com.spacehub.launchdetail.data.repository.LaunchDetailRepositoryImpl
import com.spacehub.launchdetail.domain.repository.LaunchDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LaunchDetailDataModule {
    @Provides
    fun provideLaunchesRepository(
        launchDetailService: LaunchDetailService,
        launchMapper: LaunchMapper,
    ): LaunchDetailRepository = LaunchDetailRepositoryImpl(launchDetailService, launchMapper)
}
