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

package com.lpirro.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launch_table")
data class LaunchLocal(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "launch_service_provider") val launchServiceProvider: AgencyLocal,
    @ColumnInfo(name = "mission_patches") val missionPatches: List<MissionPatchesLocal>?,
    @ColumnInfo(name = "pad") val pad: PadLocal,
    @ColumnInfo(name = "mission") val mission: MissionLocal?,
    @ColumnInfo(name = "net") val net: String?,
    @ColumnInfo(name = "window_start") val windowStart: String?,
    @ColumnInfo(name = "window_end") val windowEnd: String?,
    @ColumnInfo(name = "status") val status: StatusLocal,
    @ColumnInfo(name = "type") val type: LaunchType?,
    @ColumnInfo(name = "live_video_url") val liveVideoUrl: String?,
    @ColumnInfo(name = "info_url") val infoUrl: String?,
    @ColumnInfo(name = "flightclub_url") val flightClubUrl: String?,
    @ColumnInfo(name = "updates") val updates: List<UpdateLocal>?,
    @ColumnInfo(name = "rocket") val rocket: RocketLocal
)

@Entity(tableName = "saved_launch_table")
data class SavedLaunchLocal(
    @PrimaryKey @ColumnInfo(name = "saved_launch_id") val savedLaunchId: String,
    @Embedded val launchLocal: LaunchLocal
)

enum class LaunchType {
    PAST,
    UPCOMING,
    UNKNOWN
}
