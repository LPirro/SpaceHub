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

package com.spacehub.common.mapper.di

import com.lpirro.spacehub.core.util.DateParser
import com.spacehub.common.mapper.AgencyMapper
import com.spacehub.common.mapper.AgencyMapperImpl
import com.spacehub.common.mapper.LaunchMapper
import com.spacehub.common.mapper.LaunchMapperImpl
import com.spacehub.common.mapper.LauncherLandingMapper
import com.spacehub.common.mapper.LauncherLandingMapperImpl
import com.spacehub.common.mapper.LauncherStageMapper
import com.spacehub.common.mapper.LauncherStageMapperImpl
import com.spacehub.common.mapper.LocationMapper
import com.spacehub.common.mapper.LocationMapperImpl
import com.spacehub.common.mapper.MapPositionMapper
import com.spacehub.common.mapper.MapPositionMapperImpl
import com.spacehub.common.mapper.MissionMapper
import com.spacehub.common.mapper.MissionMapperImpl
import com.spacehub.common.mapper.MissionPatchMapper
import com.spacehub.common.mapper.MissionPatchesMapperImpl
import com.spacehub.common.mapper.OrbitMapper
import com.spacehub.common.mapper.OrbitMapperImpl
import com.spacehub.common.mapper.PadMapper
import com.spacehub.common.mapper.PadMapperImpl
import com.spacehub.common.mapper.RocketConfigurationMapper
import com.spacehub.common.mapper.RocketConfigurationMapperImpl
import com.spacehub.common.mapper.RocketMapper
import com.spacehub.common.mapper.RocketMapperImpl
import com.spacehub.common.mapper.StatusMapper
import com.spacehub.common.mapper.StatusMapperImpl
import com.spacehub.common.mapper.UpdateMapper
import com.spacehub.common.mapper.UpdateMapperImpl
import com.spacehub.common.mapper.UrlMapper
import com.spacehub.common.mapper.UrlMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

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
        urlMapper: UrlMapper,
    ): LaunchMapper = LaunchMapperImpl(
        agencyMapper = agencyMapper,
        missionPatchMapper = missionPatchMapper,
        padMapper = padMapper,
        dateParser = dateParser,
        statusMapper = statusMapper,
        missionMapper = missionMapper,
        updateMapper = updateMapper,
        rocketMapper = rocketMapper,
        urlMapper = urlMapper,
    )

    @Provides
    fun provideAgencyMapper(): AgencyMapper =
        AgencyMapperImpl()

    @Provides
    fun provideMissionPatchMapper(): MissionPatchMapper =
        MissionPatchesMapperImpl()

    @Provides
    fun provideLocationMapper(): LocationMapper = LocationMapperImpl()

    @Provides
    fun providePadMapper(
        locationMapper: LocationMapper,
        mapPositionMapper: MapPositionMapper,
    ): PadMapper =
        PadMapperImpl(locationMapper, mapPositionMapper)

    @Provides
    fun provideStatusMapper(): StatusMapper =
        StatusMapperImpl()

    @Provides
    fun provideMapPositionMapper(): MapPositionMapper = MapPositionMapperImpl()

    @Provides
    fun provideOrbitMapper(): OrbitMapper = OrbitMapperImpl()

    @Provides
    fun provideMissionMapper(orbitMapper: OrbitMapper): MissionMapper =
        MissionMapperImpl(orbitMapper)

    @Provides
    fun provideUpdateMapper(dateParser: DateParser): UpdateMapper =
        UpdateMapperImpl(dateParser)

    @Provides
    fun provideRocketMapper(
        rocketConfigurationMapper: RocketConfigurationMapper,
        launcherStageMapper: LauncherStageMapper,
    ): RocketMapper = RocketMapperImpl(
        rocketConfigurationMapper = rocketConfigurationMapper,
        launcherStageMapper = launcherStageMapper,
    )

    @Provides
    fun provideRocketConfigurationMapper(agencyMapper: AgencyMapper): RocketConfigurationMapper =
        RocketConfigurationMapperImpl(agencyMapper = agencyMapper)

    @Provides
    fun provideLauncherStageMapper(launcherLandingMapper: LauncherLandingMapper): LauncherStageMapper =
        LauncherStageMapperImpl(launcherLandingMapper = launcherLandingMapper)

    @Provides
    fun providesLauncherLandingMapper(): LauncherLandingMapper = LauncherLandingMapperImpl()

    @Provides
    fun provideUrlMapper(): UrlMapper = UrlMapperImpl()
}
