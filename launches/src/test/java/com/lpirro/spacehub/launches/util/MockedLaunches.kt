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

package com.lpirro.spacehub.launches.util

import com.lpirro.spacehub.core.model.Status
import com.lpirro.spacehub.launches.domain.model.Agency
import com.lpirro.spacehub.launches.domain.model.Launch
import com.lpirro.spacehub.launches.domain.model.LauncherStage
import com.lpirro.spacehub.launches.domain.model.Location
import com.lpirro.spacehub.launches.domain.model.MapPosition
import com.lpirro.spacehub.launches.domain.model.Mission
import com.lpirro.spacehub.launches.domain.model.MissionPatches
import com.lpirro.spacehub.launches.domain.model.Orbit
import com.lpirro.spacehub.launches.domain.model.Pad
import com.lpirro.spacehub.launches.domain.model.Rocket
import com.lpirro.spacehub.launches.domain.model.RocketConfiguration

object MockedLaunches {
    val fakeLaunch = Launch(
        id = "launch-001",
        name = "Falcon 9 - Starlink 30",
        image = "https://example.com/images/starlink30.jpg",
        launchServiceProvider = Agency(
            id = 1,
            url = "https://example.com/agency/spacex",
            name = "SpaceX",
            countryCode = "USA",
            administrator = "Elon Musk",
            foundingYear = "2002",
            totalLaunchCount = null,
            logoUrl = "https://example.com/images/spacex-logo.png",
        ),
        missionPatches = listOf(
            MissionPatches(
                id = 1,
                name = "Mission Name",
                imageUrl = "https://example.com/images/patch-001.png"
            )
        ),
        mission = Mission(
            id = 123,
            name = "Starlink 30",
            description = "Deployment of the 30th batch of Starlink satellites to low Earth orbit.",
            type = "type",
            orbit = Orbit(
                id = 123,
                name = "Low Earth Orbit",
                abbrev = "LEO"
            )
        ),
        pad = Pad(
            id = 39,
            url = "https://example.com/pads/39",
            agencyId = 1,
            name = "Launch Complex 39A",
            infoUrl = "https://example.com/pad-info/39",
            wikiUrl = "https://en.wikipedia.org/wiki/Launch_Complex_39A",
            mapUrl = "https://maps.example.com/pad/39",
            location = Location(
                id = 1,
                name = "Kennedy Space Center",
            ),
            totalLaunchCount = 130,
            orbitalLaunchAttemptCount = 100,
            mapPosition = MapPosition(
                latitude = 28.5721,
                longitude = -80.6480
            )
        ),
        netDisplay = "2024-09-25T14:30:00Z",
        windowStartDisplay = "2024-09-25T14:00:00Z",
        windowEndDisplay = "2024-09-25T15:00:00Z",
        windowEnd = "2024-09-25T15:00:00Z",
        netMillis = 1695741000000L,
        status = Status.Go(
            name = "GO",
            abbrev = "GO",
            description = "Launch Scheduled"
        ),
        youtubeVideoId = "abcd1234",
        infoUrl = "https://spacex.com/starlink30",
        flightClubUrl = "https://flightclub.io/starlink30",
        updates = null,
        rocket = Rocket(
            id = 1,
            configuration = RocketConfiguration(
                id = 9,
                name = "Falcon 9",
                manufacturer = null,
                variant = "variant",
                height = null,
                diameter = null,
                gtoCapacity = null,
                leoCapacity = null,
                toThrust = null,
                apogee = null,
                reusable = true,
                successfulLaunches = null,
                consecutiveSuccessfulLaunches = null,
                failedLaunches = null,
                pendingLaunches = null,
                launchCost = null,
                infoUrl = null,
                wikiUrl = null,
                minStage = null,
                maxStage = null,
                description = "description",
                imageUrl = null,
            ),
            launcherStage = listOf(
                LauncherStage(
                    type = "Booster",
                    serialNumber = null,
                    landing = null
                )
            )
        )
    )
}