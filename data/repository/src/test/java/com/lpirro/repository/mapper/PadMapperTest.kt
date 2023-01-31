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

import com.lpirro.domain.models.Location
import com.lpirro.domain.models.Pad
import com.lpirro.network.models.LocationRemote
import com.lpirro.persistence.model.LocationLocal
import com.lpirro.persistence.model.PadLocal
import com.lpirro.repository.mapper.util.MockMapperUtil
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PadMapperTest {

    private lateinit var padMapper: PadMapper
    private var locationMapper: LocationMapper = mock()
    private var mapPositionMapper: MapPositionMapper = mock()

    @Before
    fun setup() {
        padMapper = PadMapperImpl(locationMapper, mapPositionMapper)
        whenever(locationMapper.mapToDomain(any())).thenReturn(MockMapperUtil.mockLocation())
        whenever(locationMapper.mapToLocal(any())).thenReturn(MockMapperUtil.mockLocationLocal())
        whenever(mapPositionMapper.mapToDomain(any(), any())).thenReturn(MockMapperUtil.mockMapPosition())
    }

    @Test
    fun `PadLocal to Pad maps Correctly`() {
        val padLocal = MockMapperUtil.mockPadLocal()
        val mappedPad = padMapper.mapToDomain(padLocal)

        assertEquals(padLocal.id, mappedPad.id)
        assertEquals(padLocal.url, mappedPad.url)
        assertEquals(padLocal.agencyId, mappedPad.agencyId)
        assertEquals(padLocal.name, mappedPad.name)
        assertEquals(padLocal.infoUrl, mappedPad.infoUrl)
        assertEquals(padLocal.wikiUrl, mappedPad.wikiUrl)
        assertEquals(padLocal.mapUrl, mappedPad.mapUrl)
        assertLocation(padLocal.location, mappedPad.location)
        assertEquals(padLocal.totalLaunchCount, mappedPad.totalLaunchCount)
        assertEquals(padLocal.orbitalLaunchAttemptCount, mappedPad.orbitalLaunchAttemptCount)
        assertMapPosition(padLocal, mappedPad)
    }

    @Test
    fun `PadRemote to PadLocal maps Correctly`() {
        val padRemote = MockMapperUtil.mockPadRemote()
        val mappedPad = padMapper.mapToLocal(padRemote)

        assertEquals(padRemote.id, mappedPad.id)
        assertEquals(padRemote.url, mappedPad.url)
        assertEquals(padRemote.agencyId, mappedPad.agencyId)
        assertEquals(padRemote.name, mappedPad.name)
        assertEquals(padRemote.infoUrl, mappedPad.infoUrl)
        assertEquals(padRemote.wikiUrl, mappedPad.wikiUrl)
        assertEquals(padRemote.mapUrl, mappedPad.mapUrl)
        assertLocation(padRemote.location, mappedPad.location)
        assertEquals(padRemote.totalLaunchCount, mappedPad.totalLaunchCount)
        assertEquals(padRemote.orbitalLaunchAttemptCount, mappedPad.orbitalLaunchAttemptCount)
        assertEquals(padRemote.latitude, mappedPad.latitude)
        assertEquals(padRemote.longitude, mappedPad.longitude)
    }

    private fun assertLocation(locationLocal: LocationLocal, location: Location) {
        assertEquals(locationLocal.id, location.id)
        assertEquals(locationLocal.name, location.name)
    }

    private fun assertLocation(locationRemote: LocationRemote, locationLocal: LocationLocal) {
        assertEquals(locationRemote.id, locationLocal.id)
        assertEquals(locationRemote.name, locationLocal.name)
    }

    private fun assertMapPosition(padLocal: PadLocal, pad: Pad) {
        assertEquals(padLocal.latitude, pad.mapPosition!!.latitude.toString())
        assertEquals(padLocal.longitude, pad.mapPosition!!.longitude.toString())
    }
}
