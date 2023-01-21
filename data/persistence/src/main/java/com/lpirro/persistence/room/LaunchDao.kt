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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lpirro.persistence.model.LaunchLocal
import com.lpirro.persistence.model.LaunchType

@Dao
interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(launches: List<LaunchLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(launch: LaunchLocal)

    @Update
    suspend fun updateLaunch(launch: LaunchLocal)

    @Query("SELECT * FROM launch_table WHERE type=:launchType")
    fun getLaunchesWithType(launchType: LaunchType): List<LaunchLocal>

    @Query("SELECT * FROM launch_table WHERE id=:id")
    fun getLaunch(id: String): LaunchLocal?

    @Query("DELETE FROM launch_table WHERE type=:launchType")
    fun deleteAll(launchType: LaunchType)
}
