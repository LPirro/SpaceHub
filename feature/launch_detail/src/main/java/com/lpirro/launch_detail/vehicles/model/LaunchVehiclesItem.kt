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

package com.lpirro.launch_detail.vehicles.model

interface LaunchVehiclesItem

data class RocketUi(
    val id: Long,
    val name: String,
    val manufacturer: String,
    val variant: String,
    val height: String,
    val diameter: String,
    val gtoCapacity: String,
    val leoCapacity: String,
    val toThrust: String,
    val apogee: String,
    val reusable: Boolean,
    val successfulLaunches: String,
    val consecutiveSuccessfulLaunches: String,
    val failedLaunches: String,
    val pendingLaunches: String,
    val launchCost: String,
    val infoUrl: String?,
    val wikiUrl: String?,
    val minStage: String,
    val maxStage: String,
    val imageUrl: String?
) : LaunchVehiclesItem

data class LauncherStageUi(
    val type: String,
    val serialNumber: String,
    val landingType: String,
    val landingLocation: String
) : LaunchVehiclesItem
