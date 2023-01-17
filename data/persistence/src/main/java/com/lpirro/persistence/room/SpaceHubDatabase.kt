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

package com.lpirro.persistence.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lpirro.persistence.model.LaunchLocal
import com.lpirro.persistence.room.typeconverter.AgencyLocalTypeConverter
import com.lpirro.persistence.room.typeconverter.MissionLocalTypeConverter
import com.lpirro.persistence.room.typeconverter.MissionPatchesLocalTypeConverter
import com.lpirro.persistence.room.typeconverter.PadLocalTypeConverter
import com.lpirro.persistence.room.typeconverter.StatusLocalTypeConverter
import com.lpirro.persistence.room.typeconverter.UpdateLocalTypeConverter

@Database(entities = [LaunchLocal::class], version = 1, exportSchema = true)
@TypeConverters(value = [AgencyLocalTypeConverter::class, MissionPatchesLocalTypeConverter::class, PadLocalTypeConverter::class, StatusLocalTypeConverter::class, MissionLocalTypeConverter::class, UpdateLocalTypeConverter::class])
abstract class SpaceHubDatabase : RoomDatabase() {
    abstract fun launchDao(): LaunchDao
}
