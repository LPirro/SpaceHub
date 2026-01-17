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
package com.spacehub.common.mapper

import com.spacehub.common.models.domain.Agency
import com.spacehub.common.models.remote.AgencyRemote

interface AgencyMapper {
    fun mapToDomain(agencyRemote: AgencyRemote): Agency
}

class AgencyMapperImpl(
    private val countryCodeMapper: CountryCodeMapper,
) : AgencyMapper {
    override fun mapToDomain(agencyRemote: AgencyRemote) =
        Agency(
            id = agencyRemote.id,
            url = agencyRemote.url,
            name = agencyRemote.name,
            countries = agencyRemote.country.map { countryCodeMapper.mapToDomain(it) },
            administrator = agencyRemote.administrator,
            foundingYear = agencyRemote.foundingYear?.toString(),
            totalLaunchCount = agencyRemote.totalLaunchCount?.toString(),
            logoUrl = agencyRemote.logo?.imageUrl,
            socialLogoUrl = agencyRemote.socialLogo?.imageUrl,
        )
}
