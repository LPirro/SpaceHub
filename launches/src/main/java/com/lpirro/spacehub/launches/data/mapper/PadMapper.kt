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

import com.lpirro.spacehub.launches.data.network.model.PadRemote
import com.lpirro.spacehub.launches.domain.model.Pad

interface PadMapper {
    fun mapToDomain(padRemote: PadRemote): Pad
}

class PadMapperImpl(
    private val locationMapper: LocationMapper,
    private val mapPositionMapper: MapPositionMapper,
) : PadMapper {
    override fun mapToDomain(padRemote: PadRemote) =
        Pad(
            id = padRemote.id,
            url = padRemote.url,
            agencyId = padRemote.agencyId,
            name = padRemote.name,
            infoUrl = padRemote.infoUrl,
            wikiUrl = padRemote.wikiUrl,
            mapUrl = padRemote.mapUrl,
            location = locationMapper.mapToDomain(padRemote.location),
            totalLaunchCount = padRemote.totalLaunchCount,
            orbitalLaunchAttemptCount = padRemote.orbitalLaunchAttemptCount,
            mapPosition = mapPositionMapper.mapToDomain(padRemote.latitude, padRemote.longitude),
        )
}
