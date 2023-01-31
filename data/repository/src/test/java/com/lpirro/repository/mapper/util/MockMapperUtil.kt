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

package com.lpirro.repository.mapper.util

import com.lpirro.domain.models.Agency
import com.lpirro.domain.models.LauncherLanding
import com.lpirro.domain.models.Location
import com.lpirro.domain.models.MapPosition
import com.lpirro.domain.models.Mission
import com.lpirro.domain.models.MissionPatches
import com.lpirro.domain.models.Orbit
import com.lpirro.domain.models.Pad
import com.lpirro.domain.models.Rocket
import com.lpirro.domain.models.RocketConfiguration
import com.lpirro.domain.models.Status
import com.lpirro.network.models.AgencyRemote
import com.lpirro.network.models.LandingLocationRemote
import com.lpirro.network.models.LaunchRemote
import com.lpirro.network.models.LauncherLandingRemote
import com.lpirro.network.models.LauncherRemote
import com.lpirro.network.models.LauncherStageRemote
import com.lpirro.network.models.LauncherTypeRemote
import com.lpirro.network.models.LocationRemote
import com.lpirro.network.models.MissionPatchesRemote
import com.lpirro.network.models.MissionRemote
import com.lpirro.network.models.OrbitRemote
import com.lpirro.network.models.PadRemote
import com.lpirro.network.models.RocketConfigurationRemote
import com.lpirro.network.models.RocketRemote
import com.lpirro.network.models.StatusRemote
import com.lpirro.network.models.Urls
import com.lpirro.persistence.model.AgencyLocal
import com.lpirro.persistence.model.LaunchLocal
import com.lpirro.persistence.model.LaunchType
import com.lpirro.persistence.model.LauncherLandingLocal
import com.lpirro.persistence.model.LauncherStageLocal
import com.lpirro.persistence.model.LocationLocal
import com.lpirro.persistence.model.MissionLocal
import com.lpirro.persistence.model.MissionPatchesLocal
import com.lpirro.persistence.model.OrbitLocal
import com.lpirro.persistence.model.PadLocal
import com.lpirro.persistence.model.RocketConfigurationLocal
import com.lpirro.persistence.model.RocketLocal
import com.lpirro.persistence.model.StatusLocal

const val NET = "2022-12-25T09:30:00Z"
const val WINDOW_START = "2022-12-25T08:30:00Z"
const val WINDOW_END = "2022-12-25T10:30:00Z"

object MockMapperUtil {

    fun mockLaunchRemote() = LaunchRemote(
        id = "d955a829-0222-41d8-8f69-ee1d5f851b93",
        name = "LaunchName",
        image = "LaunchImage",
        launchServiceProvider = mockAgencyRemote(),
        missionPatches = listOf(mockMissionPatchesRemote()),
        pad = mockPadRemote(),
        net = NET,
        windowStart = WINDOW_START,
        windowEnd = WINDOW_END,
        status = mockStatusRemote(),
        videoUrls = mockVideoUrls(),
        flightClubUrl = "flightClubUrl",
        infoURLs = mockInfoUrls(),
        mission = mockMissionRemote(),
        updates = null,
        rocket = mockRocketRemote()
    )

    fun mockLaunchLocal() = LaunchLocal(
        id = "d955a829-0222-41d8-8f69-ee1d5f851b93",
        name = "LaunchName",
        image = "LaunchImage",
        launchServiceProvider = mockAgencyLocal(),
        missionPatches = listOf(mockMissionPatchesLocal()),
        pad = mockPadLocal(),
        net = NET,
        windowStart = WINDOW_START,
        windowEnd = WINDOW_END,
        status = mockStatusLocal(),
        type = LaunchType.PAST,
        liveVideoUrl = "1",
        flightClubUrl = "flightClubUrl",
        infoUrl = "infoUrl",
        mission = mockMissionLocal(),
        updates = null,
        rocket = mockRocketLocal()
    )

    private fun mockVideoUrls() = listOf(
        Urls("liveVideoUrl1"),
        Urls("liveVideoUrl2"),
    )

    private fun mockInfoUrls() = listOf(
        Urls("infoUrl1"),
        Urls("infoUrl2"),
    )

    fun mockAgencyRemote() = AgencyRemote(
        id = 1,
        name = "SpaceX",
        url = "url",
        countryCode = "USA",
        administrator = "Elon Musk",
        foundingYear = "2002",
        totalLaunchCount = 169,
        logoUrl = "agencyLogoUrl"
    )

    fun mockAgencyLocal(totalLaunchCount: Int? = 169) = AgencyLocal(
        id = 1,
        name = "SpaceX",
        url = "url",
        countryCode = "USA",
        administrator = "Elon Musk",
        foundingYear = "2002",
        totalLaunchCount = totalLaunchCount,
        logoUrl = "agencyLogoUrl"
    )

    fun mockAgency() = Agency(
        id = 1,
        name = "SpaceX",
        url = "url",
        countryCode = "USA",
        administrator = "Elon Musk",
        foundingYear = "2002",
        totalLaunchCount = "169",
        logoUrl = "agencyLogoUrl"
    )

    fun mockLocationRemote() = LocationRemote(
        id = 1,
        url = "url",
        name = "LocationName",
        totalLandingCount = 1000,
        totalLaunchCount = 2000
    )

    fun mockLocationLocal() = LocationLocal(
        id = 1,
        name = "LocationName"
    )

    fun mockLocation() = Location(
        id = 1,
        name = "LocationName"
    )

    fun mockMissionPatchesRemote() = MissionPatchesRemote(
        id = 1,
        name = "MissionPatchesName",
        imageUrl = "imageUrl"
    )

    fun mockMissionPatchesLocal() = MissionPatchesLocal(
        id = 1,
        name = "MissionPatchesName",
        imageUrl = "imageUrl"
    )

    fun mockMissionPatches() = MissionPatches(
        id = 1,
        name = "MissionPatchesName",
        imageUrl = "imageUrl"
    )

    fun mockPadRemote() = PadRemote(
        id = 1,
        url = "url",
        agencyId = 2,
        name = "PadName",
        infoUrl = "infoUrl",
        wikiUrl = "wikiUrl",
        mapUrl = "mapUrl",
        location = mockLocationRemote(),
        totalLaunchCount = 100,
        orbitalLaunchAttemptCount = 200,
        latitude = "100",
        longitude = "150"
    )

    fun mockPadLocal() = PadLocal(
        id = 1,
        url = "url",
        agencyId = 2,
        name = "PadName",
        infoUrl = "infoUrl",
        wikiUrl = "wikiUrl",
        mapUrl = "mapUrl",
        location = mockLocationLocal(),
        totalLaunchCount = 100,
        orbitalLaunchAttemptCount = 200,
        latitude = "100.0",
        longitude = "150.0"
    )

    fun mockPad() = Pad(
        id = 1,
        url = "url",
        agencyId = 2,
        name = "PadName",
        infoUrl = "infoUrl",
        wikiUrl = "wikiUrl",
        mapUrl = "mapUrl",
        location = mockLocation(),
        totalLaunchCount = 100,
        orbitalLaunchAttemptCount = 200,
        mapPosition = mockMapPosition()
    )

    fun mockStatusRemote() = StatusRemote(
        id = 1,
        name = "Go for launch",
        abbrev = "Go",
        description = "StatusDescription"
    )

    fun mockStatusLocal() = StatusLocal(
        id = 1,
        name = "Go for launch",
        abbrev = "Go",
        description = "StatusDescription"
    )

    fun mockStatus() = Status.Go(
        name = "Go for launch",
        abbrev = "Go",
        description = "StatusDescription"
    )

    fun mockMissionRemote() = MissionRemote(
        id = 213,
        name = "MissionName",
        description = "MissionDescription",
        type = "MissionType",
        orbit = mockOrbitRemote()
    )

    fun mockMissionLocal() = MissionLocal(
        id = 213,
        name = "MissionName",
        description = "MissionDescription",
        type = "MissionType",
        orbit = mockOrbitLocal()
    )

    fun mockMission() = Mission(
        id = 213,
        name = "MissionName",
        description = "MissionDescription",
        type = "MissionType",
        orbit = mockOrbit()
    )

    fun mockOrbitRemote() = OrbitRemote(213, "OrbitName", "OrbitAbbrev")

    fun mockOrbitLocal() = OrbitLocal(213, "OrbitName", "OrbitAbbrev")

    fun mockOrbit() = Orbit(213, "OrbitName", "OrbitAbbrev")

    fun mockMapPosition() = MapPosition(100.0, 150.0)

    fun mockRocketRemote() = RocketRemote(
        id = 243,
        configuration = mockRocketConfigurationRemote(),
        launcherStage = emptyList()
    )

    fun mockRocketLocal() = RocketLocal(
        id = 243,
        configuration = mockRocketConfigurationLocal(),
        launcherStage = emptyList()
    )

    fun mockRocket() = Rocket(
        id = 243,
        configuration = mockRocketConfiguration(),
        launcherStage = emptyList()
    )

    fun mockRocketConfigurationRemote() = RocketConfigurationRemote(
        id = 432,
        name = "Name",
        manufacturer = mockAgencyRemote(),
        variant = "Variant",
        height = 46.16,
        diameter = 3.0,
        gtoCapacity = 100,
        leoCapacity = 200,
        toThrust = 3200,
        apogee = 40320,
        reusable = true,
        successfulLaunches = 129,
        consecutiveSuccessfulLaunches = 100,
        failedLaunches = 10,
        pendingLaunches = 0,
        launchCost = "10000000",
        infoUrl = "infoUrl",
        wikiUrl = "wikiUrl",
        minStage = 1,
        maxStage = 2,
        description = "description",
        imageUrl = "imageUrl"
    )

    fun mockRocketConfigurationLocal() = RocketConfigurationLocal(
        id = 432,
        name = "Name",
        manufacturer = mockAgencyLocal(),
        variant = "Variant",
        height = 46.16,
        diameter = 3.0,
        gtoCapacity = 100,
        leoCapacity = 200,
        toThrust = 3200,
        apogee = 40320,
        reusable = true,
        successfulLaunches = 129,
        consecutiveSuccessfulLaunches = 100,
        failedLaunches = 10,
        pendingLaunches = 0,
        launchCost = "10000000",
        infoUrl = "infoUrl",
        wikiUrl = "wikiUrl",
        minStage = 1,
        maxStage = 2,
        description = "description",
        imageUrl = "imageUrl"
    )

    fun mockRocketConfiguration() = RocketConfiguration(
        id = 432,
        name = "Name",
        manufacturer = mockAgency(),
        variant = "Variant",
        height = 46.16,
        diameter = 3.0,
        gtoCapacity = 100,
        leoCapacity = 200,
        toThrust = 3200,
        apogee = 40320,
        reusable = true,
        successfulLaunches = 129,
        consecutiveSuccessfulLaunches = 100,
        failedLaunches = 10,
        pendingLaunches = 0,
        launchCost = "10000000",
        infoUrl = "infoUrl",
        wikiUrl = "wikiUrl",
        minStage = 1,
        maxStage = 2,
        description = "description",
        imageUrl = "imageUrl"
    )

    fun mockLauncherStageRemote() = LauncherStageRemote(
        type = "Core",
        launcher = LauncherRemote(serialNumber = "1234"),
        launcherLanding = mockLauncherLandingRemote()
    )

    fun mockLauncherStageLocal() = LauncherStageLocal(
        type = "Core",
        serialNumber = "1234",
        landing = mockLauncherLandingLocal()
    )

    fun mockLauncherLandingRemote() = LauncherLandingRemote(
        type = LauncherTypeRemote("landingType"),
        landingLocation = LandingLocationRemote("locationName")
    )

    fun mockLauncherLandingLocal() = LauncherLandingLocal(
        type = "landingType",
        locationName = "locationName"
    )

    fun mockLauncherLanding() = LauncherLanding(
        type = "landingType",
        locationName = "locationName"
    )
}
