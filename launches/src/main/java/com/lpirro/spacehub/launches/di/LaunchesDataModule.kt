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
package com.lpirro.spacehub.launches.di

import com.lpirro.spacehub.core.util.DateParser
import com.spacehub.common.mapper.AgencyMapper
import com.spacehub.common.mapper.AgencyMapperImpl
import com.spacehub.common.mapper.LaunchMapper
import com.spacehub.common.mapper.LaunchMapperImpl
import com.lpirro.spacehub.launches.data.mapper.LauncherLandingMapper
import com.lpirro.spacehub.launches.data.mapper.LauncherLandingMapperImpl
import com.lpirro.spacehub.launches.data.mapper.LauncherStageMapper
import com.lpirro.spacehub.launches.data.mapper.LauncherStageMapperImpl
import com.lpirro.spacehub.launches.data.mapper.LocationMapper
import com.lpirro.spacehub.launches.data.mapper.LocationMapperImpl
import com.lpirro.spacehub.launches.data.mapper.MapPositionMapper
import com.lpirro.spacehub.launches.data.mapper.MapPositionMapperImpl
import com.spacehub.common.mapper.MissionMapper
import com.spacehub.common.mapper.MissionMapperImpl
import com.spacehub.common.mapper.MissionPatchMapper
import com.spacehub.common.mapper.MissionPatchesMapperImpl
import com.lpirro.spacehub.launches.data.mapper.OrbitMapper
import com.lpirro.spacehub.launches.data.mapper.OrbitMapperImpl
import com.spacehub.common.mapper.PadMapper
import com.spacehub.common.mapper.PadMapperImpl
import com.lpirro.spacehub.launches.data.mapper.RocketConfigurationMapper
import com.lpirro.spacehub.launches.data.mapper.RocketConfigurationMapperImpl
import com.spacehub.common.mapper.RocketMapper
import com.spacehub.common.mapper.RocketMapperImpl
import com.spacehub.common.mapper.StatusMapper
import com.spacehub.common.mapper.StatusMapperImpl
import com.spacehub.common.mapper.UpdateMapper
import com.spacehub.common.mapper.UpdateMapperImpl
import com.lpirro.spacehub.launches.data.network.LaunchesService
import com.lpirro.spacehub.launches.data.repository.LaunchesRepositoryImpl
import com.lpirro.spacehub.launches.domain.repository.LaunchesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LaunchesDataModule {
    @Provides
    fun provideLaunchesRepository(
        launchesService: LaunchesService,
        launchMapper: com.spacehub.common.mapper.LaunchMapper,
    ): LaunchesRepository = LaunchesRepositoryImpl(launchesService, launchMapper)

    @Provides
    fun provideLaunchMapper(
        agencyMapper: com.spacehub.common.mapper.AgencyMapper,
        missionPatchMapper: com.spacehub.common.mapper.MissionPatchMapper,
        padMapper: com.spacehub.common.mapper.PadMapper,
        dateParser: DateParser,
        statusMapper: com.spacehub.common.mapper.StatusMapper,
        missionMapper: com.spacehub.common.mapper.MissionMapper,
        updateMapper: com.spacehub.common.mapper.UpdateMapper,
        rocketMapper: com.spacehub.common.mapper.RocketMapper,
    ): com.spacehub.common.mapper.LaunchMapper = com.spacehub.common.mapper.LaunchMapperImpl(
        agencyMapper = agencyMapper,
        missionPatchMapper = missionPatchMapper,
        padMapper = padMapper,
        dateParser = dateParser,
        statusMapper = statusMapper,
        missionMapper = missionMapper,
        updateMapper = updateMapper,
        rocketMapper = rocketMapper,
    )

    @Provides
    fun provideAgencyMapper(): com.spacehub.common.mapper.AgencyMapper =
        com.spacehub.common.mapper.AgencyMapperImpl()

    @Provides
    fun provideMissionPatchMapper(): com.spacehub.common.mapper.MissionPatchMapper =
        com.spacehub.common.mapper.MissionPatchesMapperImpl()

    @Provides
    fun provideLocationMapper(): LocationMapper = LocationMapperImpl()

    @Provides
    fun providePadMapper(
        locationMapper: LocationMapper,
        mapPositionMapper: MapPositionMapper,
    ): com.spacehub.common.mapper.PadMapper =
        com.spacehub.common.mapper.PadMapperImpl(locationMapper, mapPositionMapper)

    @Provides
    fun provideStatusMapper(): com.spacehub.common.mapper.StatusMapper =
        com.spacehub.common.mapper.StatusMapperImpl()

    @Provides
    fun provideMapPositionMapper(): MapPositionMapper = MapPositionMapperImpl()

    @Provides
    fun provideOrbitMapper(): OrbitMapper = OrbitMapperImpl()

    @Provides
    fun provideMissionMapper(orbitMapper: OrbitMapper): com.spacehub.common.mapper.MissionMapper =
        com.spacehub.common.mapper.MissionMapperImpl(orbitMapper)

    @Provides
    fun provideUpdateMapper(dateParser: DateParser): com.spacehub.common.mapper.UpdateMapper =
        com.spacehub.common.mapper.UpdateMapperImpl(dateParser)

    @Provides
    fun provideRocketMapper(
        rocketConfigurationMapper: RocketConfigurationMapper,
        launcherStageMapper: LauncherStageMapper,
    ): com.spacehub.common.mapper.RocketMapper = com.spacehub.common.mapper.RocketMapperImpl(
        rocketConfigurationMapper = rocketConfigurationMapper,
        launcherStageMapper = launcherStageMapper,
    )

    @Provides
    fun provideRocketConfigurationMapper(agencyMapper: com.spacehub.common.mapper.AgencyMapper): RocketConfigurationMapper = RocketConfigurationMapperImpl(agencyMapper = agencyMapper)

    @Provides
    fun provideLauncherStageMapper(launcherLandingMapper: LauncherLandingMapper): LauncherStageMapper = LauncherStageMapperImpl(launcherLandingMapper = launcherLandingMapper)

    @Provides
    fun providesLauncherLandingMapper(): LauncherLandingMapper = LauncherLandingMapperImpl()
}
