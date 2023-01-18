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

package com.lpirro.repository.mapper

import com.lpirro.domain.models.Launch
import com.lpirro.network.models.LaunchRemote
import com.lpirro.persistence.model.LaunchLocal
import com.lpirro.persistence.model.LaunchType

class LaunchMapperImpl(
    private val agencyMapper: AgencyMapper,
    private val missionPatchMapper: MissionPatchMapper,
    private val padMapper: PadMapper,
    private val dateParser: DateParser,
    private val statusMapper: StatusMapper,
    private val youTubeVideoIdParser: YouTubeVideoIdParser,
    private val missionMapper: MissionMapper,
    private val updateMapper: UpdateMapper,
    private val rocketMapper: RocketMapper
) : LaunchMapper {
    override fun mapToDomain(launchLocal: LaunchLocal) = Launch(
        id = launchLocal.id,
        name = launchLocal.name,
        image = launchLocal.image,
        launchServiceProvider = agencyMapper.mapToDomain(launchLocal.launchServiceProvider),
        missionPatches = launchLocal.missionPatches?.map { missionPatchMapper.mapToDomain(it) },
        pad = padMapper.mapToDomain(launchLocal.pad),
        mission = launchLocal.mission?.let { missionMapper.mapToDomain(it) },
        net = launchLocal.net?.let { dateParser.parseFullDate(it) },
        windowStart = launchLocal.windowStart?.let { dateParser.parseFullDate(it) },
        windowEnd = launchLocal.windowEnd?.let { dateParser.parseFullDate(it) },
        netMillis = launchLocal.net?.let { dateParser.parseDateInMillis(it) },
        status = statusMapper.mapToDomain(launchLocal.status),
        youtubeVideoId = launchLocal.liveVideoUrl?.let { youTubeVideoIdParser.getVideoId(it) },
        infoUrl = launchLocal.infoUrl,
        flightClubUrl = launchLocal.flightClubUrl,
        updates = launchLocal.updates?.map { updateMapper.mapToDomain(it) },
        rocket = rocketMapper.mapToDomain(launchLocal.rocket)
    )

    override fun mapToLocal(launchRemote: LaunchRemote, launchType: LaunchType?) = LaunchLocal(
        id = launchRemote.id,
        name = launchRemote.name,
        image = launchRemote.image,
        launchServiceProvider = agencyMapper.mapToLocal(launchRemote.launchServiceProvider),
        missionPatches = launchRemote.missionPatches?.map { missionPatchMapper.mapToLocal(it) },
        pad = padMapper.mapToLocal(launchRemote.pad),
        mission = launchRemote.mission?.let { missionMapper.mapToLocal(it) },
        net = launchRemote.net,
        windowStart = launchRemote.windowStart,
        windowEnd = launchRemote.windowEnd,
        status = statusMapper.mapToLocal(launchRemote.status),
        type = launchType,
        liveVideoUrl = launchRemote.videoUrls.firstOrNull()?.url,
        infoUrl = launchRemote.infoURLs?.firstOrNull()?.url,
        flightClubUrl = launchRemote.flightClubUrl,
        updates = launchRemote.updates?.map { updateMapper.mapToLocal(it) },
        rocket = rocketMapper.mapToLocal(launchRemote.rocket)
    )
}
