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

import com.lpirro.domain.models.Agency
import com.lpirro.network.models.AgencyRemote
import com.lpirro.persistence.model.AgencyLocal

class AgencyMapperImpl : AgencyMapper {
    override fun mapToDomain(agencyLocal: AgencyLocal) = Agency(
        id = agencyLocal.id,
        url = agencyLocal.url,
        name = agencyLocal.name,
        countryCode = agencyLocal.countryCode,
        administrator = agencyLocal.administrator ?: "N/A",
        foundingYear = agencyLocal.foundingYear ?: "N/A",
        totalLaunchCount = agencyLocal.totalLaunchCount?.toString() ?: "N/A",
        logoUrl = agencyLocal.logoUrl
    )

    override fun mapToLocal(agencyRemote: AgencyRemote) = AgencyLocal(
        id = agencyRemote.id,
        url = agencyRemote.url,
        name = agencyRemote.name,
        countryCode = agencyRemote.countryCode,
        administrator = agencyRemote.administrator,
        foundingYear = agencyRemote.foundingYear,
        totalLaunchCount = agencyRemote.totalLaunchCount,
        logoUrl = agencyRemote.logoUrl
    )
}
