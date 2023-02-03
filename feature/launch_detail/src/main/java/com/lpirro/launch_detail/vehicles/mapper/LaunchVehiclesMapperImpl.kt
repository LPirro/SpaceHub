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

package com.lpirro.launch_detail.vehicles.mapper

import com.lpirro.core.extensions.asDollars
import com.lpirro.core.extensions.asKiloMeters
import com.lpirro.core.extensions.asKiloNewton
import com.lpirro.core.extensions.asKilograms
import com.lpirro.core.extensions.asMeters
import com.lpirro.core.extensions.prependPoundSignSymbol
import com.lpirro.domain.models.LauncherStage
import com.lpirro.domain.models.Rocket
import com.lpirro.launch_detail.vehicles.model.LaunchVehiclesItem
import com.lpirro.launch_detail.vehicles.model.LauncherStageUi
import com.lpirro.launch_detail.vehicles.model.RocketUi

const val NOT_AVAILABLE_PLACEHOLDER = "N/A"

class LaunchVehiclesMapperImpl : LaunchVehiclesMapper {

    override fun mapToUi(rocket: Rocket): List<LaunchVehiclesItem> {
        val launchVehiclesItems = mutableListOf<LaunchVehiclesItem>()

        launchVehiclesItems.add(getRocketUi(rocket))
        if (rocket.launcherStage.isNotEmpty()) {
            launchVehiclesItems.addAll(rocket.launcherStage.map { getLauncherStageUi(it) })
        }

        return launchVehiclesItems
    }

    private fun getRocketUi(rocket: Rocket) = RocketUi(
        id = rocket.id,
        name = rocket.configuration.name,
        manufacturer = rocket.configuration.manufacturer?.name ?: NOT_AVAILABLE_PLACEHOLDER,
        variant = rocket.configuration.variant,
        height = rocket.configuration.height.asMeters() ?: NOT_AVAILABLE_PLACEHOLDER,
        diameter = rocket.configuration.diameter.asMeters() ?: NOT_AVAILABLE_PLACEHOLDER,
        gtoCapacity = rocket.configuration.gtoCapacity.asKilograms() ?: NOT_AVAILABLE_PLACEHOLDER,
        leoCapacity = rocket.configuration.leoCapacity.asKilograms() ?: NOT_AVAILABLE_PLACEHOLDER,
        toThrust = rocket.configuration.toThrust.asKiloNewton() ?: NOT_AVAILABLE_PLACEHOLDER,
        apogee = rocket.configuration.apogee.asKiloMeters() ?: NOT_AVAILABLE_PLACEHOLDER,
        reusable = rocket.configuration.reusable,
        successfulLaunches = rocket.configuration.successfulLaunches?.toString()
            ?: NOT_AVAILABLE_PLACEHOLDER,
        consecutiveSuccessfulLaunches = rocket.configuration.consecutiveSuccessfulLaunches?.toString()
            ?: NOT_AVAILABLE_PLACEHOLDER,
        failedLaunches = rocket.configuration.failedLaunches?.toString()
            ?: NOT_AVAILABLE_PLACEHOLDER,
        pendingLaunches = rocket.configuration.pendingLaunches?.toString()
            ?: NOT_AVAILABLE_PLACEHOLDER,
        launchCost = rocket.configuration.launchCost.asDollars() ?: NOT_AVAILABLE_PLACEHOLDER,
        infoUrl = rocket.configuration.infoUrl,
        wikiUrl = rocket.configuration.wikiUrl,
        minStage = rocket.configuration.minStage?.toString() ?: NOT_AVAILABLE_PLACEHOLDER,
        maxStage = rocket.configuration.maxStage?.toString() ?: NOT_AVAILABLE_PLACEHOLDER,
        imageUrl = rocket.configuration.imageUrl
    )

    private fun getLauncherStageUi(launcherStage: LauncherStage) = LauncherStageUi(
        type = launcherStage.type,
        serialNumber = launcherStage.serialNumber.prependPoundSignSymbol() ?: NOT_AVAILABLE_PLACEHOLDER,
        landingType = launcherStage.landing?.type ?: NOT_AVAILABLE_PLACEHOLDER,
        landingLocation = launcherStage.landing?.locationName ?: NOT_AVAILABLE_PLACEHOLDER
    )
}
