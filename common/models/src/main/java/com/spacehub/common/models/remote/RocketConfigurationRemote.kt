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
package com.spacehub.common.models.remote

import com.google.gson.annotations.SerializedName

data class RocketConfigurationRemote(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("manufacturer") val manufacturer: AgencyRemote?,
    @SerializedName("variant") val variant: String,
    @SerializedName("length") val height: Double?,
    @SerializedName("diameter") val diameter: Double?,
    @SerializedName("gto_capacity") val gtoCapacity: Double?,
    @SerializedName("leo_capacity") val leoCapacity: Double?,
    @SerializedName("to_thrust") val toThrust: Double?,
    @SerializedName("apogee") val apogee: Double?,
    @SerializedName("reusable") val reusable: Boolean,
    @SerializedName("successful_launches") val successfulLaunches: Int?,
    @SerializedName("consecutive_successful_launches") val consecutiveSuccessfulLaunches: Int?,
    @SerializedName("failed_launches") val failedLaunches: Int?,
    @SerializedName("pending_launches") val pendingLaunches: Int?,
    @SerializedName("launch_cost") val launchCost: Long?,
    @SerializedName("info_url") val infoUrl: String?,
    @SerializedName("wiki_url") val wikiUrl: String?,
    @SerializedName("min_stage") val minStage: Int?,
    @SerializedName("max_stage") val maxStage: Int?,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: ImageRemote?,
)
