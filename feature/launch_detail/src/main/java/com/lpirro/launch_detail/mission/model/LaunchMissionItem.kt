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

package com.lpirro.launch_detail.mission.model

import com.lpirro.domain.models.Update

interface LaunchMissionItem

data class MissionHeaderUi(
    val name: String,
    val orbit: String,
    val agencyName: String,
    val missionType: String,
    val missionPatchUrl: String?
) : LaunchMissionItem

data class DescriptionUi(
    val description: String,
    val infoUrl: String?
) : LaunchMissionItem

data class LaunchInfoUi(
    val status: String,
    val statusDescription: String,
    val net: String,
    val windowStart: String,
    val windowEnd: String
) : LaunchMissionItem

data class UpdatesUi(
    val updates: List<Update>
) : LaunchMissionItem

object MissionDetailsUnavailableUi : LaunchMissionItem
