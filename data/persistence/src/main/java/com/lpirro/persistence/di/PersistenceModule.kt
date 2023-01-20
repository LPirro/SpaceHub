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

package com.lpirro.persistence.di

import android.app.Application
import androidx.room.Room
import com.lpirro.persistence.room.ArticleDao
import com.lpirro.persistence.room.LaunchDao
import com.lpirro.persistence.room.SpaceHubDatabase
import com.lpirro.persistence.room.typeconverter.AgencyLocalTypeConverter
import com.lpirro.persistence.room.typeconverter.MissionLocalTypeConverter
import com.lpirro.persistence.room.typeconverter.MissionPatchesLocalTypeConverter
import com.lpirro.persistence.room.typeconverter.PadLocalTypeConverter
import com.lpirro.persistence.room.typeconverter.RelatedLaunchLocalTypeConverter
import com.lpirro.persistence.room.typeconverter.RocketLocalTypeConverter
import com.lpirro.persistence.room.typeconverter.StatusLocalTypeConverter
import com.lpirro.persistence.room.typeconverter.UpdateLocalTypeConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PersistenceModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application,
        agencyLocalTypeConverter: AgencyLocalTypeConverter,
        missionPatchesLocalTypeConverter: MissionPatchesLocalTypeConverter,
        padLocalTypeConverter: PadLocalTypeConverter,
        statusLocalTypeConverter: StatusLocalTypeConverter,
        missionLocalTypeConverter: MissionLocalTypeConverter,
        updateLocalTypeConverter: UpdateLocalTypeConverter,
        rocketLocalTypeConverter: RocketLocalTypeConverter,
        relatedLaunchLocalTypeConverter: RelatedLaunchLocalTypeConverter
    ): SpaceHubDatabase {
        return Room
            .databaseBuilder(application, SpaceHubDatabase::class.java, "spacehub.db")
            .fallbackToDestructiveMigration()
            .addTypeConverter(agencyLocalTypeConverter)
            .addTypeConverter(missionPatchesLocalTypeConverter)
            .addTypeConverter(padLocalTypeConverter)
            .addTypeConverter(statusLocalTypeConverter)
            .addTypeConverter(missionLocalTypeConverter)
            .addTypeConverter(updateLocalTypeConverter)
            .addTypeConverter(rocketLocalTypeConverter)
            .addTypeConverter(relatedLaunchLocalTypeConverter)
            .build()
    }

    @Provides
    fun providesLaunchDao(database: SpaceHubDatabase): LaunchDao {
        return database.launchDao()
    }

    @Provides
    fun providesArticleDao(database: SpaceHubDatabase): ArticleDao {
        return database.articleDao()
    }

    @Provides
    fun providesAgencyTypeConverter(): AgencyLocalTypeConverter {
        return AgencyLocalTypeConverter()
    }

    @Provides
    fun providesMissionPatchesTypeConverter(): MissionPatchesLocalTypeConverter {
        return MissionPatchesLocalTypeConverter()
    }

    @Provides
    fun providesPadTypeConverter(): PadLocalTypeConverter {
        return PadLocalTypeConverter()
    }

    @Provides
    fun provideStatusTypeConverter(): StatusLocalTypeConverter {
        return StatusLocalTypeConverter()
    }

    @Provides
    fun provideMissionLocalTypeConverter(): MissionLocalTypeConverter {
        return MissionLocalTypeConverter()
    }

    @Provides
    fun provideUpdateLocalTypeConverter(): UpdateLocalTypeConverter {
        return UpdateLocalTypeConverter()
    }

    @Provides
    fun provideRocketLocalTypeConverter(): RocketLocalTypeConverter {
        return RocketLocalTypeConverter()
    }

    @Provides
    fun provideRelatedLaunchLocalTypeConverter(): RelatedLaunchLocalTypeConverter {
        return RelatedLaunchLocalTypeConverter()
    }
}
