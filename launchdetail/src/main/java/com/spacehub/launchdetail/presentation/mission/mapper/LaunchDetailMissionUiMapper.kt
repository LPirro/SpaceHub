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

package com.spacehub.launchdetail.presentation.mission.mapper

import com.lpirro.spacehub.core.util.DateParser
import com.spacehub.common.models.domain.Launch
import com.spacehub.launchdetail.presentation.mission.model.DescriptionSection
import com.spacehub.launchdetail.presentation.mission.model.LaunchDetailMissionUi
import com.spacehub.launchdetail.presentation.mission.model.LaunchInfoSection
import com.spacehub.launchdetail.presentation.mission.model.MissionHeaderUi
import com.spacehub.launchdetail.presentation.mission.model.UpdatesSection

internal class LaunchDetailMissionUiMapperImpl(
    private val dateParser: DateParser,
) : LaunchDetailMissionUiMapper {
    override fun mapToUi(launch: Launch) = LaunchDetailMissionUi(
        missionHeaderSection = MissionHeaderUi(
            name = launch.mission?.name ?: "N/A",
            orbit = launch.mission?.orbit?.name ?: "N/A",
            type = launch.mission?.type ?: "N/A",
            agencyName = launch.launchServiceProvider.name,
            missionPatchImageUrl = launch.missionPatches?.firstOrNull()?.imageUrl,
        ),
        descriptionSection = DescriptionSection(
            description = launch.mission?.description ?: "N/A",
            moreInfoUrl = launch.infoUrl,
        ),
        launchInfoSection = LaunchInfoSection(
            status = launch.status,
            net = launch.net,
            windowStart = launch.windowStart?.let { dateParser.parseFullDate(it) } ?: "N/A",
            windowEnd = launch.windowEnd?.let { dateParser.parseFullDate(it) } ?: "N/A",
        ),
        updatesSection = launch.updates?.let { updates ->
            UpdatesSection(updates = updates)
        },
    )
}

interface LaunchDetailMissionUiMapper {
    fun mapToUi(launch: Launch): LaunchDetailMissionUi
}
