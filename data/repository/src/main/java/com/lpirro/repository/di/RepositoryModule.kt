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

package com.lpirro.repository.di

import com.lpirro.domain.repository.LaunchDetailRepository
import com.lpirro.domain.repository.LaunchesRepository
import com.lpirro.domain.repository.NewsRepository
import com.lpirro.domain.repository.SavedLaunchesRepository
import com.lpirro.network.LaunchLibraryApiService
import com.lpirro.network.NewsApiService
import com.lpirro.persistence.room.ArticleDao
import com.lpirro.persistence.room.LaunchDao
import com.lpirro.repository.LaunchDetailRepositoryImpl
import com.lpirro.repository.LaunchesRepositoryImpl
import com.lpirro.repository.NewsRepositoryImpl
import com.lpirro.repository.SavedLaunchesRepositoryImpl
import com.lpirro.repository.mapper.AgencyMapper
import com.lpirro.repository.mapper.AgencyMapperImpl
import com.lpirro.repository.mapper.ArticleMapper
import com.lpirro.repository.mapper.ArticleMapperImpl
import com.lpirro.repository.mapper.DateParser
import com.lpirro.repository.mapper.DateParserImpl
import com.lpirro.repository.mapper.LaunchMapper
import com.lpirro.repository.mapper.LaunchMapperImpl
import com.lpirro.repository.mapper.LauncherLandingMapper
import com.lpirro.repository.mapper.LauncherLandingMapperImpl
import com.lpirro.repository.mapper.LauncherStageMapper
import com.lpirro.repository.mapper.LauncherStageMapperImpl
import com.lpirro.repository.mapper.LocationMapper
import com.lpirro.repository.mapper.LocationMapperImpl
import com.lpirro.repository.mapper.MapPositionMapper
import com.lpirro.repository.mapper.MapPositionMapperImpl
import com.lpirro.repository.mapper.MissionMapper
import com.lpirro.repository.mapper.MissionMapperImpl
import com.lpirro.repository.mapper.MissionPatchMapper
import com.lpirro.repository.mapper.MissionPatchesMapperImpl
import com.lpirro.repository.mapper.OrbitMapper
import com.lpirro.repository.mapper.OrbitMapperImpl
import com.lpirro.repository.mapper.PadMapper
import com.lpirro.repository.mapper.PadMapperImpl
import com.lpirro.repository.mapper.RocketConfigurationMapper
import com.lpirro.repository.mapper.RocketConfigurationMapperImpl
import com.lpirro.repository.mapper.RocketMapper
import com.lpirro.repository.mapper.RocketMapperImpl
import com.lpirro.repository.mapper.StatusMapper
import com.lpirro.repository.mapper.StatusMapperImpl
import com.lpirro.repository.mapper.UpdateMapper
import com.lpirro.repository.mapper.UpdateMapperImpl
import com.lpirro.repository.mapper.YouTubeVideoIdParser
import com.lpirro.repository.mapper.YouTubeVideoIdParserImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideLaunchesRepository(
        apiService: LaunchLibraryApiService,
        launchDao: LaunchDao,
        launchMapper: LaunchMapper
    ): LaunchesRepository {
        return LaunchesRepositoryImpl(apiService, launchDao, launchMapper)
    }

    @Provides
    fun provideLaunchDetailRepository(
        launchLibraryApiService: LaunchLibraryApiService,
        launchDao: LaunchDao,
        launchMapper: LaunchMapper
    ): LaunchDetailRepository {
        return LaunchDetailRepositoryImpl(launchLibraryApiService, launchDao, launchMapper)
    }

    @Provides
    fun provideSavedLaunchesRepository(
        launchDao: LaunchDao,
        launchMapper: LaunchMapper
    ): SavedLaunchesRepository {
        return SavedLaunchesRepositoryImpl(launchDao, launchMapper)
    }

    @Provides
    fun provideNewsRepository(
        newsApiService: NewsApiService,
        articleDao: ArticleDao,
        articleMapper: ArticleMapper
    ): NewsRepository {
        return NewsRepositoryImpl(newsApiService, articleDao, articleMapper)
    }

    @Provides
    fun provideArticleMapper(dateParser: DateParser): ArticleMapper {
        return ArticleMapperImpl(dateParser)
    }

    @Provides
    fun provideLaunchMapper(
        agencyMapper: AgencyMapper,
        missionPatchMapper: MissionPatchMapper,
        padMapper: PadMapper,
        dateParser: DateParser,
        statusMapper: StatusMapper,
        youTubeVideoIdParser: YouTubeVideoIdParser,
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
            youTubeVideoIdParser = youTubeVideoIdParser,
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
    fun provideYouTubeVideoIdParser(): YouTubeVideoIdParser = YouTubeVideoIdParserImpl()

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
