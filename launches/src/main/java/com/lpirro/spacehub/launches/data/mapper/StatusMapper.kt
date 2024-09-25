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

package com.lpirro.spacehub.launches.data.mapper

import com.lpirro.spacehub.launches.data.network.model.StatusRemote
import com.lpirro.spacehub.core.model.Status

interface StatusMapper {
    fun mapToDomain(statusRemote: StatusRemote): Status
}

class StatusMapperImpl : StatusMapper {
    override fun mapToDomain(statusRemote: StatusRemote): Status {
        return when (statusRemote.id) {
            1 -> Status.Go(
                name = statusRemote.name,
                abbrev = statusRemote.abbrev,
                description = statusRemote.description
            )

            2 -> Status.TBD(
                name = statusRemote.name,
                abbrev = statusRemote.abbrev,
                description = statusRemote.description
            )

            3 -> Status.Success(
                name = statusRemote.name,
                abbrev = statusRemote.abbrev,
                description = statusRemote.description
            )

            4 -> Status.Failure(
                name = statusRemote.name,
                abbrev = statusRemote.abbrev,
                description = statusRemote.description
            )

            6 -> Status.InFlight(
                name = statusRemote.name,
                abbrev = statusRemote.abbrev,
                description = statusRemote.description
            )

            8 -> Status.TBC(
                name = statusRemote.name,
                abbrev = statusRemote.abbrev,
                description = statusRemote.description
            )

            else -> Status.Unknown(
                name = statusRemote.name,
                abbrev = statusRemote.abbrev,
                description = statusRemote.description
            )
        }
    }
}