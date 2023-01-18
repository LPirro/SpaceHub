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

import com.lpirro.domain.models.RocketConfiguration
import com.lpirro.network.models.RocketConfigurationRemote
import com.lpirro.persistence.model.RocketConfigurationLocal

class RocketConfigurationMapperImpl(private val agencyMapper: AgencyMapper) : RocketConfigurationMapper {

    override fun mapToDomain(rocketConfigurationLocal: RocketConfigurationLocal) =
        RocketConfiguration(
            id = rocketConfigurationLocal.id,
            name = rocketConfigurationLocal.name,
            manufacturer = rocketConfigurationLocal.manufacturer?.let { agencyMapper.mapToDomain(it) },
            variant = rocketConfigurationLocal.variant,
            height = rocketConfigurationLocal.height,
            diameter = rocketConfigurationLocal.diameter,
            gtoCapacity = rocketConfigurationLocal.gtoCapacity,
            leoCapacity = rocketConfigurationLocal.leoCapacity,
            toThrust = rocketConfigurationLocal.toThrust,
            apogee = rocketConfigurationLocal.apogee,
            reusable = rocketConfigurationLocal.reusable,
            successfulLaunches = rocketConfigurationLocal.successfulLaunches,
            consecutiveSuccessfulLaunches = rocketConfigurationLocal.consecutiveSuccessfulLaunches,
            failedLaunches = rocketConfigurationLocal.failedLaunches,
            pendingLaunches = rocketConfigurationLocal.pendingLaunches,
            launchCost = rocketConfigurationLocal.launchCost,
            infoUrl = rocketConfigurationLocal.infoUrl,
            wikiUrl = rocketConfigurationLocal.wikiUrl,
            minStage = rocketConfigurationLocal.minStage,
            maxStage = rocketConfigurationLocal.maxStage,
            description = rocketConfigurationLocal.description,
            imageUrl = rocketConfigurationLocal.imageUrl
        )

    override fun mapToLocal(rocketConfigurationRemote: RocketConfigurationRemote) =
        RocketConfigurationLocal(
            id = rocketConfigurationRemote.id,
            name = rocketConfigurationRemote.name,
            manufacturer = rocketConfigurationRemote.manufacturer?.let { agencyMapper.mapToLocal(it) },
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
