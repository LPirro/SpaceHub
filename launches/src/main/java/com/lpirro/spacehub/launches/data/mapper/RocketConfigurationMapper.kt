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

import com.lpirro.spacehub.launches.data.network.model.RocketConfigurationRemote
import com.lpirro.spacehub.launches.domain.model.RocketConfiguration

interface RocketConfigurationMapper {
    fun mapToDomain(rocketConfigurationRemote: RocketConfigurationRemote): RocketConfiguration
}

class RocketConfigurationMapperImpl(private val agencyMapper: AgencyMapper) :
    RocketConfigurationMapper {
    override fun mapToDomain(rocketConfigurationRemote: RocketConfigurationRemote) =
        RocketConfiguration(
            id = rocketConfigurationRemote.id,
            name = rocketConfigurationRemote.name,
            manufacturer = rocketConfigurationRemote.manufacturer?.let { agencyMapper.mapToDomain(it) },
            variant = rocketConfigurationRemote.variant,
            height = rocketConfigurationRemote.height,
            diameter = rocketConfigurationRemote.diameter,
            gtoCapacity = rocketConfigurationRemote.gtoCapacity,
            leoCapacity = rocketConfigurationRemote.leoCapacity,
            toThrust = rocketConfigurationRemote.toThrust,
            apogee = rocketConfigurationRemote.apogee,
            reusable = rocketConfigurationRemote.reusable,
            successfulLaunches = rocketConfigurationRemote.successfulLaunches,
            consecutiveSuccessfulLaunches = rocketConfigurationRemote.consecutiveSuccessfulLaunches,
            failedLaunches = rocketConfigurationRemote.failedLaunches,
            pendingLaunches = rocketConfigurationRemote.pendingLaunches,
            launchCost = rocketConfigurationRemote.launchCost,
            infoUrl = rocketConfigurationRemote.infoUrl,
            wikiUrl = rocketConfigurationRemote.wikiUrl,
            minStage = rocketConfigurationRemote.minStage,
            maxStage = rocketConfigurationRemote.maxStage,
            description = rocketConfigurationRemote.description,
            imageUrl = rocketConfigurationRemote.imageUrl
        )
}