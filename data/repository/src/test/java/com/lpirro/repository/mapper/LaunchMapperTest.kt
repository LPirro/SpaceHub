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

package com.lpirro.repository.mapper

import com.lpirro.domain.models.Agency
import com.lpirro.domain.models.Location
import com.lpirro.domain.models.Mission
import com.lpirro.domain.models.MissionPatches
import com.lpirro.domain.models.Orbit
import com.lpirro.domain.models.Pad
import com.lpirro.domain.models.Status
import com.lpirro.network.models.AgencyRemote
import com.lpirro.network.models.LocationRemote
import com.lpirro.network.models.MissionPatchesRemote
import com.lpirro.network.models.MissionRemote
import com.lpirro.network.models.OrbitRemote
import com.lpirro.network.models.PadRemote
import com.lpirro.network.models.StatusRemote
import com.lpirro.persistence.model.AgencyLocal
import com.lpirro.persistence.model.LaunchType
import com.lpirro.persistence.model.LocationLocal
import com.lpirro.persistence.model.MissionLocal
import com.lpirro.persistence.model.MissionPatchesLocal
import com.lpirro.persistence.model.OrbitLocal
import com.lpirro.persistence.model.PadLocal
import com.lpirro.persistence.model.StatusLocal
import com.lpirro.repository.mapper.util.MockMapperUtil
import com.lpirro.repository.mapper.util.NET
import com.lpirro.repository.mapper.util.WINDOW_END
import com.lpirro.repository.mapper.util.WINDOW_START
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

private const val EXPECTED_NET_PARSED_DATE = "25 Dec 2022 • 09:30"
private const val EXPECTED_WINDOW_START_PARSED_DATE = "25 Dec 2022 • 09:30"
private const val EXPECTED_WINDOW_END_PARSED_DATE = "25 Dec 2022 • 11:30"
private const val EXPECTED_NET_PARSED_DATE_MILLIS = 1671957000000

class LaunchMapperTest {

    private val agencyMapper: AgencyMapper = mock()
    private val missionPatchMapper: MissionPatchMapper = mock()
    private val padMapper: PadMapper = mock()
    private val dateParser: DateParser = mock()
    private val statusMapper: StatusMapper = mock()
    private val youTubeVideoIdParser: YouTubeVideoIdParser = mock()
    private val missionMapper: MissionMapper = mock()
    private val updateMapper: UpdateMapper = mock()
    private val orbitMapper: OrbitMapper = mock()
    private val rocketMapper: RocketMapper = mock()

    private lateinit var launchMapper: LaunchMapper

    @Before
    fun setup() {
        launchMapper = LaunchMapperImpl(
            agencyMapper = agencyMapper,
            missionPatchMapper = missionPatchMapper,
            padMapper = padMapper,
            dateParser = dateParser,
            statusMapper = statusMapper,
            youTubeVideoIdParser = youTubeVideoIdParser,
            missionMapper = missionMapper,
            updateMapper = updateMapper,
            rocketMapper = rocketMapper
        )

        whenever(agencyMapper.mapToDomain(any())).thenReturn(MockMapperUtil.mockAgency())
        whenever(agencyMapper.mapToLocal(any())).thenReturn(MockMapperUtil.mockAgencyLocal())
        whenever(missionPatchMapper.mapToDomain(any())).thenReturn(MockMapperUtil.mockMissionPatches())
        whenever(missionPatchMapper.mapToLocal(any())).thenReturn(MockMapperUtil.mockMissionPatchesLocal())
        whenever(padMapper.mapToDomain(any())).thenReturn(MockMapperUtil.mockPad())
        whenever(padMapper.mapToLocal(any())).thenReturn(MockMapperUtil.mockPadLocal())
        whenever(dateParser.parseFullDate(NET)).thenReturn(EXPECTED_NET_PARSED_DATE)
        whenever(dateParser.parseFullDate(WINDOW_START)).thenReturn(EXPECTED_WINDOW_START_PARSED_DATE)
        whenever(dateParser.parseFullDate(WINDOW_END)).thenReturn(EXPECTED_WINDOW_END_PARSED_DATE)
        whenever(dateParser.parseDateInMillis(any())).thenReturn(EXPECTED_NET_PARSED_DATE_MILLIS)
        whenever(statusMapper.mapToDomain(any())).thenReturn(MockMapperUtil.mockStatus())
        whenever(statusMapper.mapToLocal(any())).thenReturn(MockMapperUtil.mockStatusLocal())
        whenever(youTubeVideoIdParser.getVideoId(any())).thenReturn(MockMapperUtil.mockLaunchLocal().liveVideoUrl)
        whenever(missionMapper.mapToDomain(any())).thenReturn(MockMapperUtil.mockMission())
        whenever(missionMapper.mapToLocal(any())).thenReturn(MockMapperUtil.mockMissionLocal())
        whenever(orbitMapper.mapToDomain(any())).thenReturn(MockMapperUtil.mockOrbit())
        whenever(orbitMapper.mapToLocal(any())).thenReturn(MockMapperUtil.mockOrbitLocal())
        whenever(rocketMapper.mapToDomain(any())).thenReturn(MockMapperUtil.mockRocket())
        whenever(rocketMapper.mapToLocal(any())).thenReturn(MockMapperUtil.mockRocketLocal())
    }

    @Test
    fun `LaunchLocal to Launch maps Correctly`() {
        val launchLocal = MockMapperUtil.mockLaunchLocal()
        val mappedLaunch = launchMapper.mapToDomain(launchLocal)

        assertEquals(launchLocal.id, mappedLaunch.id)
        assertEquals(launchLocal.name, mappedLaunch.name)
        assertEquals(launchLocal.image, mappedLaunch.image)
        assertAgencyEquals(launchLocal.launchServiceProvider, mappedLaunch.launchServiceProvider)
        assertMissionPatches(launchLocal.missionPatches!!, mappedLaunch.missionPatches!!)
        assertPadEquals(launchLocal.pad, mappedLaunch.pad)
        assertMissionEquals(launchLocal.mission!!, mappedLaunch.mission!!)
        assertEquals(EXPECTED_NET_PARSED_DATE, mappedLaunch.net)
        assertEquals(EXPECTED_WINDOW_START_PARSED_DATE, mappedLaunch.windowStart)
        assertEquals(EXPECTED_WINDOW_END_PARSED_DATE, mappedLaunch.windowEnd)
        assertEquals(EXPECTED_NET_PARSED_DATE_MILLIS, mappedLaunch.netMillis)
        assertStatusEquals(launchLocal.status, mappedLaunch.status)
        assertEquals(launchLocal.liveVideoUrl, mappedLaunch.youtubeVideoId)
        assertEquals(launchLocal.infoUrl, mappedLaunch.infoUrl)
        assertEquals(launchLocal.flightClubUrl, mappedLaunch.flightClubUrl)
    }

    @Test
    fun `LaunchRemote to LaunchLocal maps Correctly`() {
        val launchRemote = MockMapperUtil.mockLaunchRemote()
        val mappedLaunch = launchMapper.mapToLocal(launchRemote, LaunchType.PAST)

        assertEquals(launchRemote.id, mappedLaunch.id)
        assertEquals(launchRemote.name, mappedLaunch.name)
        assertEquals(launchRemote.image, mappedLaunch.image)
        assertAgencyEquals(launchRemote.launchServiceProvider, mappedLaunch.launchServiceProvider)
        assertMissionPatchesRemote(launchRemote.missionPatches!!, mappedLaunch.missionPatches!!)
        assertPadEquals(launchRemote.pad, mappedLaunch.pad)
        assertMissionEquals(launchRemote.mission!!, mappedLaunch.mission!!)
        assertEquals(launchRemote.net, mappedLaunch.net)
        assertEquals(launchRemote.windowStart, mappedLaunch.windowStart)
        assertEquals(launchRemote.windowEnd, mappedLaunch.windowEnd)
        assertStatusEquals(launchRemote.status, mappedLaunch.status)
        assertEquals(launchRemote.videoUrls.firstOrNull()?.url, mappedLaunch.liveVideoUrl)
        assertEquals(launchRemote.infoURLs!!.firstOrNull()?.url, mappedLaunch.infoUrl)
        assertEquals(launchRemote.flightClubUrl, mappedLaunch.flightClubUrl)
    }

    private fun assertMissionEquals(missionLocal: MissionLocal, mission: Mission) {
        assertEquals(missionLocal.id, mission.id)
        assertEquals(missionLocal.name, mission.name)
        assertEquals(missionLocal.description, mission.description)
        assertEquals(missionLocal.type, mission.type)
        assertMissionOrbitEquals(missionLocal.orbit!!, mission.orbit!!)
    }

    private fun assertMissionOrbitEquals(orbitLocal: OrbitLocal, orbit: Orbit) {
        assertEquals(orbitLocal.id, orbit.id)
        assertEquals(orbitLocal.name, orbit.name)
        assertEquals(orbitLocal.abbrev, orbit.abbrev)
    }

    private fun assertMissionEquals(missionRemote: MissionRemote, missionLocal: MissionLocal) {
        assertEquals(missionRemote.id, missionLocal.id)
        assertEquals(missionRemote.name, missionLocal.name)
        assertEquals(missionRemote.description, missionLocal.description)
        assertEquals(missionRemote.type, missionLocal.type)
        assertMissionOrbitEquals(missionRemote.orbit!!, missionLocal.orbit!!)
    }

    private fun assertMissionOrbitEquals(orbitRemote: OrbitRemote, orbitLocal: OrbitLocal) {
        assertEquals(orbitRemote.id, orbitLocal.id)
        assertEquals(orbitRemote.name, orbitLocal.name)
        assertEquals(orbitRemote.abbrev, orbitLocal.abbrev)
    }

    private fun assertStatusEquals(statusLocal: StatusLocal, status: Status) {
        assertEquals(statusLocal.id, status.id)
        assertEquals(statusLocal.name, status.name)
        assertEquals(statusLocal.abbrev, status.abbrev)
        assertEquals(statusLocal.description, status.description)
    }

    private fun assertStatusEquals(statusRemote: StatusRemote, statusLocal: StatusLocal) {
        assertEquals(statusRemote.id, statusLocal.id)
        assertEquals(statusRemote.name, statusLocal.name)
        assertEquals(statusRemote.abbrev, statusLocal.abbrev)
        assertEquals(statusRemote.description, statusLocal.description)
    }

    private fun assertPadEquals(padLocal: PadLocal, pad: Pad) {
        assertEquals(padLocal.id, pad.id)
        assertEquals(padLocal.url, pad.url)
        assertEquals(padLocal.agencyId, pad.agencyId)
        assertEquals(padLocal.name, pad.name)
        assertEquals(padLocal.infoUrl, pad.infoUrl)
        assertEquals(padLocal.wikiUrl, pad.wikiUrl)
        assertEquals(padLocal.mapUrl, pad.mapUrl)
        assertLocation(padLocal.location, pad.location)
        assertEquals(padLocal.totalLaunchCount, pad.totalLaunchCount)
        assertEquals(padLocal.orbitalLaunchAttemptCount, pad.orbitalLaunchAttemptCount)
    }

    private fun assertPadEquals(padRemote: PadRemote, padLocal: PadLocal) {
        assertEquals(padRemote.id, padLocal.id)
        assertEquals(padRemote.url, padLocal.url)
        assertEquals(padRemote.agencyId, padLocal.agencyId)
        assertEquals(padRemote.name, padLocal.name)
        assertEquals(padRemote.infoUrl, padLocal.infoUrl)
        assertEquals(padRemote.wikiUrl, padLocal.wikiUrl)
        assertEquals(padRemote.mapUrl, padLocal.mapUrl)
        assertLocation(padRemote.location, padLocal.location)
        assertEquals(padRemote.totalLaunchCount, padLocal.totalLaunchCount)
        assertEquals(padRemote.orbitalLaunchAttemptCount, padLocal.orbitalLaunchAttemptCount)
    }

    private fun assertLocation(locationLocal: LocationLocal, location: Location) {
        assertEquals(locationLocal.id, location.id)
        assertEquals(locationLocal.name, location.name)
    }

    private fun assertLocation(locationRemote: LocationRemote, locationLocal: LocationLocal) {
        assertEquals(locationRemote.id, locationLocal.id)
        assertEquals(locationRemote.name, locationLocal.name)
    }

    private fun assertAgencyEquals(agencyLocal: AgencyLocal, agency: Agency) {
        assertEquals(agencyLocal.id, agency.id)
        assertEquals(agencyLocal.name, agency.name)
        assertEquals(agencyLocal.url, agency.url)
    }

    private fun assertAgencyEquals(agencyRemote: AgencyRemote, agencyLocal: AgencyLocal) {
        assertEquals(agencyRemote.id, agencyLocal.id)
        assertEquals(agencyRemote.name, agencyLocal.name)
        assertEquals(agencyRemote.url, agencyLocal.url)
    }

    private fun assertMissionPatches(
        missionPatchesLocal: List<MissionPatchesLocal>,
        missionPatches: List<MissionPatches>
    ) {
        missionPatchesLocal.forEachIndexed { index, missionPatchRemote ->
            assertEquals(missionPatchRemote.id, missionPatches[index].id)
            assertEquals(missionPatchRemote.name, missionPatches[index].name)
            assertEquals(missionPatchRemote.imageUrl, missionPatches[index].imageUrl)
        }

        assertEquals(missionPatchesLocal.size, missionPatches.size)
    }

    private fun assertMissionPatchesRemote(
        missionPatchesRemote: List<MissionPatchesRemote>,
        missionPatchesLocal: List<MissionPatchesLocal>
    ) {
        missionPatchesRemote.forEachIndexed { index, missionPatchRemote ->
            assertEquals(missionPatchRemote.id, missionPatchesLocal[index].id)
            assertEquals(missionPatchRemote.name, missionPatchesLocal[index].name)
            assertEquals(missionPatchRemote.imageUrl, missionPatchesLocal[index].imageUrl)
        }

        assertEquals(missionPatchesRemote.size, missionPatchesLocal.size)
    }
}
