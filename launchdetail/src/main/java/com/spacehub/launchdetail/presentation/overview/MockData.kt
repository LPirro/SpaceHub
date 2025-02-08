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

package com.spacehub.launchdetail.presentation.overview

import com.spacehub.launchdetail.BuildConfig
import com.spacehub.launchdetail.presentation.overview.model.AgencyUi
import com.spacehub.launchdetail.presentation.overview.model.CountdownUi
import com.spacehub.launchdetail.presentation.overview.model.LaunchOverviewUi
import com.spacehub.launchdetail.presentation.overview.model.LaunchpadUi

object MockData {

    private val latitude = 21.283333
    private val longitude = 112.0
    private val zoom = 7
    private val apiKey = BuildConfig.MAPS_API_KEY

    val mapUrl =
        "https://maps.googleapis.com/maps/api/staticmap?scale=2&center=$latitude,$longitude&zoom=$zoom&size=800x800&key=$apiKey"


    val launchOverviewUiMock = LaunchOverviewUi(
        countdownSection = CountdownUi(
            launchDate = "2023-03-30T00:00:00Z",
            targetDateMillis = 1679827200000,
        ),
        launchpadSection = LaunchpadUi(
            name = "Kennedy Space Center",
            location = "Florida, USA",
            totalLaunchCount = "124",
            infoUrl = "https://www.kennedyspacecenter.com/",
            wikiUrl = "https://en.wikipedia.org/wiki/Kennedy_Space_Center",
            mapUrl = mapUrl,
            mapImageHeaderUrl = mapUrl,
        ),
        watchLiveUrl = "https://www.youtube.com/watch?v=123",
        agencySection = AgencyUi(
            name = "NASA",
            countryCode = "USA",
            administrator = "Bill Nelson",
            foundingYear = "1958",
            totalLaunchCount = "124",
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/9/9f/NASA_logo.svg",
        ),
        trajectoryUrl = "https://www.youtube.com/watch?v=123",
    )
}
