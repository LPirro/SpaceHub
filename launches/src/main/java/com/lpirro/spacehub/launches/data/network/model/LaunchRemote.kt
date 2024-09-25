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

data class LaunchRemote(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String?,
    @SerializedName("launch_service_provider") val launchServiceProvider: AgencyRemote,
    @SerializedName("mission_patches") val missionPatches: List<MissionPatchesRemote>?,
    @SerializedName("pad") val pad: PadRemote,
    @SerializedName("mission") val mission: MissionRemote?,
    @SerializedName("net") val net: String?,
    @SerializedName("window_start") val windowStart: String?,
    @SerializedName("window_end") val windowEnd: String?,
    @SerializedName("status") val status: StatusRemote,
    @SerializedName("vidURLs") val videoUrls: List<Urls>,
    @SerializedName("infoURLs") val infoURLs: List<Urls>?,
    @SerializedName("flightclub_url") val flightClubUrl: String?,
    @SerializedName("updates") val updates: List<UpdateRemote>?,
    @SerializedName("rocket") val rocket: RocketRemote,
)
