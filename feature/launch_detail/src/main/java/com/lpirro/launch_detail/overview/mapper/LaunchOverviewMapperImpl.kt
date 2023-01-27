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

package com.lpirro.launch_detail.overview.mapper

import com.google.android.gms.maps.model.LatLng
import com.lpirro.domain.models.Agency
import com.lpirro.domain.models.Launch
import com.lpirro.domain.models.MapPosition
import com.lpirro.domain.models.Pad
import com.lpirro.launch_detail.overview.model.AgencyUi
import com.lpirro.launch_detail.overview.model.CountdownHeaderUi
import com.lpirro.launch_detail.overview.model.LaunchOverviewItem
import com.lpirro.launch_detail.overview.model.LaunchpadUi
import com.lpirro.launch_detail.overview.model.LocationUi
import com.lpirro.launch_detail.overview.model.PastLaunchHeaderUi
import com.lpirro.launch_detail.overview.model.TrajectoryUi
import com.lpirro.launch_detail.overview.model.WatchLiveUi
import com.lpirro.repository.mapper.DateParser

class LaunchOverviewMapperImpl(
    private val dateParser: DateParser
) : LaunchOverviewMapper {

    override fun mapToUi(launch: Launch, isSaved: Boolean): List<LaunchOverviewItem> {
        val launchOverviewItems = mutableListOf<LaunchOverviewItem>()

        launchOverviewItems.add(getHeader(launch, isSaved))
        launchOverviewItems.add(getLaunchpadUi(launch.pad))
        launch.youtubeVideoId?.let { launchOverviewItems.add(getWatchLiveUi(it)) }
        launchOverviewItems.add(getAgencyUi(launch.launchServiceProvider))
        launch.pad.mapPosition?.let { launchOverviewItems.add(getLocationUi(launch.pad.name, it)) }
        launch.flightClubUrl?.let { launchOverviewItems.add(getTrajectoryUi(it)) }
        return launchOverviewItems
    }

    private fun getCountdownHeaderUi(launch: Launch, isSaved: Boolean) = CountdownHeaderUi(
        launchId = launch.id,
        name = launch.name,
        launchDate = launch.netDisplay,
        netMillis = launch.netMillis,
        isSaved = isSaved
    )

    private fun getPastLaunchHeaderUi(launch: Launch, isSaved: Boolean) = PastLaunchHeaderUi(
        launchId = launch.id,
        status = launch.status,
        launchDate = launch.windowEnd?.let { dateParser.formatToDDMMMYYYY(it) },
        isSaved = isSaved
    )

    private fun getLaunchpadUi(pad: Pad) = LaunchpadUi(
        name = pad.name,
        locationName = pad.location.name,
        totalLaunchCount = pad.totalLaunchCount.toString(),
        infoUrl = pad.infoUrl,
        wikiUrl = pad.wikiUrl,
        mapUrl = pad.mapUrl
    )

    private fun getWatchLiveUi(youtubeVideoId: String) = WatchLiveUi(
        youtubeVideoId = youtubeVideoId
    )

    private fun getAgencyUi(agency: Agency) = AgencyUi(
        name = agency.name,
        countryCode = agency.countryCode,
        administrator = agency.administrator,
        foundingYear = agency.foundingYear,
        totalLaunchCount = agency.totalLaunchCount,
        logoUrl = agency.logoUrl
    )

    private fun getLocationUi(name: String, mapPosition: MapPosition) = LocationUi(
        name = name,
        latLng = LatLng(mapPosition.latitude, mapPosition.longitude)
    )

    private fun getTrajectoryUi(flightClubUrl: String) = TrajectoryUi(
        flightClubUrl = flightClubUrl
    )

    private fun getHeader(launch: Launch, isSaved: Boolean): LaunchOverviewItem {
        return when (System.currentTimeMillis() >= launch.netMillis!!) {
            true -> getPastLaunchHeaderUi(launch, isSaved)
            false -> getCountdownHeaderUi(launch, isSaved)
        }
    }
}
