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

package com.lpirro.spacehub.launches.data.network.model

import com.google.gson.annotations.SerializedName

data class PadRemote(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val url: String,
    @SerializedName("agency_id") val agencyId: Int?,
    @SerializedName("name") val name: String,
    @SerializedName("info_url") val infoUrl: String?,
    @SerializedName("wiki_url") val wikiUrl: String?,
    @SerializedName("map_url") val mapUrl: String?,
    @SerializedName("location") val location: LocationRemote,
    @SerializedName("total_launch_count") val totalLaunchCount: Int?,
    @SerializedName("orbital_launch_attempt_count") val orbitalLaunchAttemptCount: Int?,
    @SerializedName("latitude") val latitude: String?,
    @SerializedName("longitude") val longitude: String?
)
