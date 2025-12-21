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

package com.spacehub.launchdetail.presentation.overview.mapper

import com.spacehub.core.util.DateParser
import com.spacehub.common.models.domain.Launch
import com.spacehub.launchdetail.presentation.overview.model.AgencyUi
import com.spacehub.launchdetail.presentation.overview.model.CountdownUi
import com.spacehub.launchdetail.presentation.overview.model.LaunchOverviewUi
import com.spacehub.launchdetail.presentation.overview.model.LaunchpadUi
import com.spacehub.launchdetail.presentation.overview.model.WatchLiveUi

internal class LaunchDetailOverviewUiMapperImpl(
    private val googleMapsImageUrlMapper: GoogleMapsImageUrlMapper,
    private val dateParser: DateParser,
) : LaunchDetailOverviewUiMapper {
    override fun mapToUi(launch: Launch): LaunchOverviewUi = LaunchOverviewUi(
        countdownSection = CountdownUi(
            launchDate = dateParser.parseFullDate(launch.net),
            targetDateMillis = launch.netMillis,
        ),
        launchpadSection = LaunchpadUi(
            name = launch.pad.name,
            location = launch.pad.location.name,
            totalLaunchCount = launch.pad.totalLaunchCount.toString(),
            infoUrl = launch.pad.infoUrl,
            wikiUrl = launch.pad.wikiUrl,
            mapUrl = launch.pad.mapUrl,
            mapImageHeaderUrl = launch.pad.mapPosition?.let {
                googleMapsImageUrlMapper.map(it.latitude, it.longitude)
            },
        ),
        watchLiveSection = launch.watchLiveUrls?.firstOrNull()?.let {
            WatchLiveUi(
                imageUrl = it.featuredImageUrl,
                videoUrl = it.url,
            )
        },
        agencySection = AgencyUi(
            name = launch.launchServiceProvider.name,
            countryCode = launch.launchServiceProvider.countryCode,
            administrator = launch.launchServiceProvider.administrator ?: "N/A",
            foundingYear = launch.launchServiceProvider.foundingYear ?: "N/A",
            totalLaunchCount = launch.launchServiceProvider.totalLaunchCount ?: "N/A",
            logoUrl = launch.launchServiceProvider.logoUrl,
        ),
        trajectoryUrl = launch.flightClubUrl,
    )
}

interface LaunchDetailOverviewUiMapper {
    fun mapToUi(launch: Launch): LaunchOverviewUi
}
