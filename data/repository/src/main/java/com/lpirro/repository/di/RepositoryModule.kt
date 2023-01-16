package com.lpirro.repository.di

import android.content.Context
import com.lpirro.domain.repository.LaunchDetailRepository
import com.lpirro.domain.repository.LaunchesRepository
import com.lpirro.network.SpaceHubApiService
import com.lpirro.persistence.room.LaunchDao
import com.lpirro.repository.LaunchDetailRepositoryImpl
import com.lpirro.repository.LaunchesRepositoryImpl
import com.lpirro.repository.mapper.AgencyMapper
import com.lpirro.repository.mapper.AgencyMapperImpl
import com.lpirro.repository.mapper.DateParser
import com.lpirro.repository.mapper.DateParserImpl
import com.lpirro.repository.mapper.LaunchMapper
import com.lpirro.repository.mapper.LaunchMapperImpl
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
import com.lpirro.repository.mapper.StatusMapper
import com.lpirro.repository.mapper.StatusMapperImpl
import com.lpirro.repository.mapper.UpdateMapper
import com.lpirro.repository.mapper.UpdateMapperImpl
import com.lpirro.repository.mapper.YouTubeVideoIdParser
import com.lpirro.repository.mapper.YouTubeVideoIdParserImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideLaunchesRepository(
        apiService: SpaceHubApiService,
        launchDao: LaunchDao,
        launchMapper: LaunchMapper
    ): LaunchesRepository {
        return LaunchesRepositoryImpl(apiService, launchDao, launchMapper)
    }

    @Provides
    fun provideLaunchDetailRepository(
        launchDao: LaunchDao,
        launchMapper: LaunchMapper
    ): LaunchDetailRepository {
        return LaunchDetailRepositoryImpl(launchDao, launchMapper)
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
        updateMapper: UpdateMapper
    ): LaunchMapper {
        return LaunchMapperImpl(
            agencyMapper = agencyMapper,
            missionPatchMapper = missionPatchMapper,
            padMapper = padMapper,
            dateParser = dateParser,
            statusMapper = statusMapper,
            youTubeVideoIdParser = youTubeVideoIdParser,
            missionMapper = missionMapper,
            updateMapper = updateMapper
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
}
