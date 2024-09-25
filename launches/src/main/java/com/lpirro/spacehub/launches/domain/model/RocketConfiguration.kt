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
package com.lpirro.spacehub.launches.domain.model

data class RocketConfiguration(
    val id: Int,
    val name: String,
    val manufacturer: Agency?,
    val variant: String,
    val height: Double?,
    val diameter: Double?,
    val gtoCapacity: Long?,
    val leoCapacity: Long?,
    val toThrust: Long?,
    val apogee: Long?,
    val reusable: Boolean,
    val successfulLaunches: Int?,
    val consecutiveSuccessfulLaunches: Int?,
    val failedLaunches: Int?,
    val pendingLaunches: Int?,
    val launchCost: String?,
    val infoUrl: String?,
    val wikiUrl: String?,
    val minStage: Int?,
    val maxStage: Int?,
    val description: String,
    val imageUrl: String?,
)
