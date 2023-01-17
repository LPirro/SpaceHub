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

import com.lpirro.repository.mapper.util.MockMapperUtil
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AgencyMapperTest {

    private lateinit var agencyMapper: AgencyMapper

    @Before
    fun setup() {
        agencyMapper = AgencyMapperImpl()
    }

    @Test
    fun `AgencyLocal to Agency maps Correctly`() {
        val agencyLocal = MockMapperUtil.mockAgencyLocal()
        val mappedAgency = agencyMapper.mapToDomain(agencyLocal)

        assertEquals(agencyLocal.id, mappedAgency.id)
        assertEquals(agencyLocal.url, mappedAgency.url)
        assertEquals(agencyLocal.name, mappedAgency.name)
        assertEquals(agencyLocal.countryCode, mappedAgency.countryCode)
        assertEquals(agencyLocal.administrator, mappedAgency.administrator)
        assertEquals(agencyLocal.foundingYear, mappedAgency.foundingYear)
        assertEquals(agencyLocal.totalLaunchCount, mappedAgency.totalLaunchCount.toInt())
        assertEquals(agencyLocal.logoUrl, mappedAgency.logoUrl)
    }

    @Test
    fun `AgencyLocal to Agency returns NA when totalLaunchCount is null`() {
        val agencyLocal = MockMapperUtil.mockAgencyLocal(totalLaunchCount = null)
        val mappedAgency = agencyMapper.mapToDomain(agencyLocal)

        assertEquals("N/A", mappedAgency.totalLaunchCount)
    }

    @Test
    fun `AgencyRemote to AgencyLocal maps Correctly`() {
        val agencyRemote = MockMapperUtil.mockAgencyRemote()
        val mappedAgency = agencyMapper.mapToLocal(agencyRemote)

        assertEquals(agencyRemote.id, mappedAgency.id)
        assertEquals(agencyRemote.url, mappedAgency.url)
        assertEquals(agencyRemote.name, mappedAgency.name)
        assertEquals(agencyRemote.countryCode, mappedAgency.countryCode)
        assertEquals(agencyRemote.administrator, mappedAgency.administrator)
        assertEquals(agencyRemote.foundingYear, mappedAgency.foundingYear)
        assertEquals(agencyRemote.totalLaunchCount, mappedAgency.totalLaunchCount)
        assertEquals(agencyRemote.logoUrl, mappedAgency.logoUrl)
    }
}
