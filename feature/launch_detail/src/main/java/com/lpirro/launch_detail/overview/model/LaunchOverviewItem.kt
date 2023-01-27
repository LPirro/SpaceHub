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

package com.lpirro.launch_detail.overview.model

import com.google.android.gms.maps.model.LatLng
import com.lpirro.domain.models.Status

interface LaunchOverviewItem

data class CountdownHeaderUi(
    val launchId: String,
    val name: String,
    val launchDate: String?,
    val netMillis: Long?,
    val isSaved: Boolean
) : LaunchOverviewItem {
    fun isSavedChanged(saved: Boolean): Boolean {
        return saved == this.isSaved
    }
}

data class PastLaunchHeaderUi(
    val launchId: String,
    val status: Status,
    val launchDate: String?,
    val isSaved: Boolean
) : LaunchOverviewItem

data class LaunchpadUi(
    val name: String,
    val locationName: String,
    val totalLaunchCount: String,
    val infoUrl: String?,
    val wikiUrl: String?,
    val mapUrl: String?,
) : LaunchOverviewItem

data class WatchLiveUi(
    val youtubeVideoId: String
) : LaunchOverviewItem

data class AgencyUi(
    val name: String,
    val countryCode: String,
    val administrator: String,
    val foundingYear: String,
    val totalLaunchCount: String,
    val logoUrl: String?
) : LaunchOverviewItem

data class LocationUi(
    val name: String,
    val latLng: LatLng
) : LaunchOverviewItem

data class TrajectoryUi(
    val flightClubUrl: String
) : LaunchOverviewItem
