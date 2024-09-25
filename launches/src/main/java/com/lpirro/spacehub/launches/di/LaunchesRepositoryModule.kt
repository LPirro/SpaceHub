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

package com.lpirro.spacehub.launches.di

import com.lpirro.spacehub.launches.data.mapper.AgencyMapper
import com.lpirro.spacehub.launches.data.mapper.AgencyMapperImpl
import com.lpirro.spacehub.launches.data.mapper.DateParser
import com.lpirro.spacehub.launches.data.mapper.DateParserImpl
import com.lpirro.spacehub.launches.data.mapper.LaunchMapper
import com.lpirro.spacehub.launches.data.mapper.LaunchMapperImpl
import com.lpirro.spacehub.launches.data.mapper.LauncherLandingMapper
import com.lpirro.spacehub.launches.data.mapper.LauncherLandingMapperImpl
import com.lpirro.spacehub.launches.data.mapper.LauncherStageMapper
import com.lpirro.spacehub.launches.data.mapper.LauncherStageMapperImpl
import com.lpirro.spacehub.launches.data.mapper.LocationMapper
import com.lpirro.spacehub.launches.data.mapper.LocationMapperImpl
import com.lpirro.spacehub.launches.data.mapper.MapPositionMapper
import com.lpirro.spacehub.launches.data.mapper.MapPositionMapperImpl
import com.lpirro.spacehub.launches.data.mapper.MissionMapper
import com.lpirro.spacehub.launches.data.mapper.MissionMapperImpl
import com.lpirro.spacehub.launches.data.mapper.MissionPatchMapper
import com.lpirro.spacehub.launches.data.mapper.MissionPatchesMapperImpl
import com.lpirro.spacehub.launches.data.mapper.OrbitMapper
import com.lpirro.spacehub.launches.data.mapper.OrbitMapperImpl
import com.lpirro.spacehub.launches.data.mapper.PadMapper
import com.lpirro.spacehub.launches.data.mapper.PadMapperImpl
import com.lpirro.spacehub.launches.data.mapper.RocketConfigurationMapper
import com.lpirro.spacehub.launches.data.mapper.RocketConfigurationMapperImpl
import com.lpirro.spacehub.launches.data.mapper.RocketMapper
import com.lpirro.spacehub.launches.data.mapper.RocketMapperImpl
import com.lpirro.spacehub.launches.data.mapper.StatusMapper
import com.lpirro.spacehub.launches.data.mapper.StatusMapperImpl
import com.lpirro.spacehub.launches.data.mapper.UpdateMapper
import com.lpirro.spacehub.launches.data.mapper.UpdateMapperImpl
import com.lpirro.spacehub.launches.data.network.LaunchesService
import com.lpirro.spacehub.launches.data.repository.LaunchesRepositoryImpl
import com.lpirro.spacehub.launches.domain.repository.LaunchesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LaunchesRepositoryModule {

    @Provides
    fun provideLaunchesRepository(
        launchesService: LaunchesService,
        launchMapper: LaunchMapper
    ): LaunchesRepository {
        return LaunchesRepositoryImpl(launchesService, launchMapper)
    }

    @Provides
    fun provideLaunchMapper(
        agencyMapper: AgencyMapper,
        missionPatchMapper: MissionPatchMapper,
        padMapper: PadMapper,
        dateParser: DateParser,
        statusMapper: StatusMapper,
        missionMapper: MissionMapper,
        updateMapper: UpdateMapper,
        rocketMapper: RocketMapper,
    ): LaunchMapper {
        return LaunchMapperImpl(
            agencyMapper = agencyMapper,
            missionPatchMapper = missionPatchMapper,
            padMapper = padMapper,
            dateParser = dateParser,
            statusMapper = statusMapper,
            missionMapper = missionMapper,
            updateMapper = updateMapper,
            rocketMapper = rocketMapper
        )
    }

    @Provides
    fun provideAgencyMapper(): AgencyMapper = AgencyMapperImpl()

    @Provides
    fun provideMissionPatchMapper(): MissionPatchMapper = MissionPatchesMapperImpl()

    @Provides
    fun provideLocationMapper(): LocationMapper = LocationMapperImpl()

    @Provides
    fun providePadMapper(
        locationMapper: LocationMapper,
        mapPositionMapper: MapPositionMapper
    ): PadMapper = PadMapperImpl(locationMapper, mapPositionMapper)

    @Provides
    fun provideDateParser(): DateParser = DateParserImpl()

    @Provides
    fun provideStatusMapper(): StatusMapper = StatusMapperImpl()

    @Provides
    fun provideMapPositionMapper(): MapPositionMapper = MapPositionMapperImpl()

    @Provides
    fun provideOrbitMapper(): OrbitMapper = OrbitMapperImpl()

    @Provides
    fun provideMissionMapper(orbitMapper: OrbitMapper): MissionMapper {
        return MissionMapperImpl(orbitMapper)
    }

    @Provides
    fun provideUpdateMapper(dateParser: DateParser): UpdateMapper {
        return UpdateMapperImpl(dateParser)
    }

    @Provides
    fun provideRocketMapper(
        rocketConfigurationMapper: RocketConfigurationMapper,
        launcherStageMapper: LauncherStageMapper
    ): RocketMapper {
        return RocketMapperImpl(
            rocketConfigurationMapper = rocketConfigurationMapper,
            launcherStageMapper = launcherStageMapper
        )
    }

    @Provides
    fun provideRocketConfigurationMapper(agencyMapper: AgencyMapper): RocketConfigurationMapper {
        return RocketConfigurationMapperImpl(agencyMapper = agencyMapper)
    }

    @Provides
    fun provideLauncherStageMapper(launcherLandingMapper: LauncherLandingMapper): LauncherStageMapper {
        return LauncherStageMapperImpl(launcherLandingMapper = launcherLandingMapper)
    }

    @Provides
    fun providesLauncherLandingMapper(): LauncherLandingMapper {
        return LauncherLandingMapperImpl()
    }
}