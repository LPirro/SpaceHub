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
package com.lpirro.spacehub.launches.data.mapper

import com.lpirro.spacehub.core.util.DateParser
import com.lpirro.spacehub.launches.data.network.model.LaunchRemote
import com.lpirro.spacehub.launches.domain.model.Launch

interface LaunchMapper {
    fun mapToDomain(launchRemote: LaunchRemote): Launch
}

class LaunchMapperImpl(
    private val agencyMapper: AgencyMapper,
    private val missionPatchMapper: MissionPatchMapper,
    private val padMapper: PadMapper,
    private val dateParser: DateParser,
    private val statusMapper: StatusMapper,
    private val missionMapper: MissionMapper,
    private val updateMapper: UpdateMapper,
    private val rocketMapper: RocketMapper,
) : LaunchMapper {
    override fun mapToDomain(launchRemote: LaunchRemote) =
        Launch(
            id = launchRemote.id,
            name = launchRemote.name,
            image = launchRemote.image,
            launchServiceProvider = agencyMapper.mapToDomain(launchRemote.launchServiceProvider),
            missionPatches = launchRemote.missionPatches?.map { missionPatchMapper.mapToDomain(it) },
            pad = padMapper.mapToDomain(launchRemote.pad),
            mission = launchRemote.mission?.let { missionMapper.mapToDomain(it) },
            netDisplay = launchRemote.net?.let { dateParser.parseFullDate(it) },
            windowStartDisplay = launchRemote.windowStart?.let { dateParser.parseFullDate(it) },
            windowEndDisplay = launchRemote.windowEnd?.let { dateParser.parseFullDate(it) },
            windowEnd = launchRemote.windowEnd,
            netMillis = launchRemote.net?.let { dateParser.parseDateInMillis(it) },
            status = statusMapper.mapToDomain(launchRemote.status),
            youtubeVideoId = null,
            infoUrl = launchRemote.infoURLs?.firstOrNull()?.url,
            flightClubUrl = launchRemote.flightClubUrl,
            updates = launchRemote.updates?.map { updateMapper.mapToDomain(it) },
            rocket = rocketMapper.mapToDomain(launchRemote.rocket),
        )
}
