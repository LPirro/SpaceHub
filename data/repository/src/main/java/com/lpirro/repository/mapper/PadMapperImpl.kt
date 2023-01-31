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

package com.lpirro.repository.mapper

import com.lpirro.domain.models.Pad
import com.lpirro.network.models.PadRemote
import com.lpirro.persistence.model.PadLocal

class PadMapperImpl(
    private val locationMapper: LocationMapper,
    private val mapPositionMapper: MapPositionMapper
) : PadMapper {
    override fun mapToDomain(padLocal: PadLocal) = Pad(
        id = padLocal.id,
        url = padLocal.url,
        agencyId = padLocal.agencyId,
        name = padLocal.name,
        infoUrl = padLocal.infoUrl,
        wikiUrl = padLocal.wikiUrl,
        mapUrl = padLocal.mapUrl,
        location = locationMapper.mapToDomain(padLocal.location),
        totalLaunchCount = padLocal.totalLaunchCount,
        orbitalLaunchAttemptCount = padLocal.orbitalLaunchAttemptCount,
        mapPosition = mapPositionMapper.mapToDomain(padLocal.latitude, padLocal.longitude)
    )

    override fun mapToLocal(padRemote: PadRemote) = PadLocal(
        id = padRemote.id,
        url = padRemote.url,
        agencyId = padRemote.agencyId,
        name = padRemote.name,
        infoUrl = padRemote.infoUrl,
        wikiUrl = padRemote.wikiUrl,
        mapUrl = padRemote.mapUrl,
        location = locationMapper.mapToLocal(padRemote.location),
        totalLaunchCount = padRemote.totalLaunchCount,
        orbitalLaunchAttemptCount = padRemote.orbitalLaunchAttemptCount,
        longitude = padRemote.longitude,
        latitude = padRemote.latitude
    )
}
