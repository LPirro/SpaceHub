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

package com.lpirro.launch_detail.mission.mapper

import com.lpirro.domain.models.Launch
import com.lpirro.domain.models.Mission
import com.lpirro.domain.models.Update
import com.lpirro.launch_detail.mission.model.DescriptionUi
import com.lpirro.launch_detail.mission.model.LaunchInfoUi
import com.lpirro.launch_detail.mission.model.LaunchMissionItem
import com.lpirro.launch_detail.mission.model.MissionDetailsUnavailableUi
import com.lpirro.launch_detail.mission.model.MissionHeaderUi
import com.lpirro.launch_detail.mission.model.UpdatesUi

class LaunchMissionMapperImpl : LaunchMissionMapper {

    override fun mapToUi(launch: Launch): List<LaunchMissionItem> {
        val launchMissionItems = mutableListOf<LaunchMissionItem>()

        launch.mission?.let { mission ->
            launchMissionItems.add(
                getMissionHeader(
                    mission,
                    launch.launchServiceProvider.name,
                    launch.missionPatches?.firstOrNull()?.imageUrl
                )
            )
            launchMissionItems.add(getMissionDescription(mission, launch.infoUrl))
            launchMissionItems.add(getLaunchInfoUi(launch))
            launch.updates?.let { launchMissionItems.add(getUpdatesUi(it)) }
        }

        if (launch.mission == null) {
            launchMissionItems.add(MissionDetailsUnavailableUi)
        }

        return launchMissionItems
    }

    private fun getMissionHeader(mission: Mission, agencyName: String, missionPatchUrl: String?) =
        MissionHeaderUi(
            name = mission.name,
            orbit = mission.orbit?.name ?: "N/A",
            agencyName = agencyName,
            missionType = mission.type,
            missionPatchUrl = missionPatchUrl
        )

    private fun getMissionDescription(mission: Mission, infoUrl: String?) = DescriptionUi(
        description = mission.description,
        infoUrl = infoUrl
    )

    private fun getLaunchInfoUi(launch: Launch) = LaunchInfoUi(
        status = launch.status,
        net = launch.netDisplay ?: "N/A",
        windowStart = launch.windowStartDisplay ?: "N/A",
        windowEnd = launch.windowEndDisplay ?: "N/A",
    )

    private fun getUpdatesUi(updates: List<Update>) = UpdatesUi(updates = updates)
}
