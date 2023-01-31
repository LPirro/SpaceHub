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

package com.lpirro.repository.mapper

import com.lpirro.domain.models.Orbit
import com.lpirro.network.models.OrbitRemote
import com.lpirro.persistence.model.OrbitLocal
import com.lpirro.repository.mapper.util.MockMapperUtil
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MissionMapperTest {

    lateinit var missionMapper: MissionMapper
    private var orbitMapper: OrbitMapper = mock()

    @Before
    fun setup() {
        missionMapper = MissionMapperImpl(orbitMapper)
        whenever(orbitMapper.mapToLocal(any())).thenReturn(MockMapperUtil.mockOrbitLocal())
        whenever(orbitMapper.mapToDomain(any())).thenReturn(MockMapperUtil.mockOrbit())
    }

    @Test
    fun `MissionLocal to Mission maps Correctly`() {
        val missionLocal = MockMapperUtil.mockMissionLocal()
        val mappedMission = missionMapper.mapToDomain(missionLocal)

        assertEquals(missionLocal.id, mappedMission.id)
        assertEquals(missionLocal.name, mappedMission.name)
        assertEquals(missionLocal.description, mappedMission.description)
        assertEquals(missionLocal.type, mappedMission.type)
        assertOrbit(missionLocal.orbit!!, mappedMission.orbit!!)
    }

    @Test
    fun `MissionRemote to MissionLocal maps Correctly`() {
        val missionRemote = MockMapperUtil.mockMissionRemote()
        val mappedMission = missionMapper.mapToLocal(missionRemote)

        assertEquals(missionRemote.id, mappedMission.id)
        assertEquals(missionRemote.name, mappedMission.name)
        assertEquals(missionRemote.description, mappedMission.description)
        assertEquals(missionRemote.type, mappedMission.type)
        assertOrbit(missionRemote.orbit!!, mappedMission.orbit!!)
    }

    private fun assertOrbit(orbitLocal: OrbitLocal, orbit: Orbit) {
        assertEquals(orbitLocal.id, orbit.id)
        assertEquals(orbitLocal.name, orbit.name)
        assertEquals(orbitLocal.abbrev, orbit.abbrev)
    }

    private fun assertOrbit(orbitRemote: OrbitRemote, orbitLocal: OrbitLocal) {
        assertEquals(orbitRemote.id, orbitLocal.id)
        assertEquals(orbitRemote.name, orbitLocal.name)
        assertEquals(orbitRemote.abbrev, orbitLocal.abbrev)
    }
}
