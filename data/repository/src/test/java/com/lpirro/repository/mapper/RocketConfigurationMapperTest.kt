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
import com.lpirro.network.models.AgencyRemote
import com.lpirro.persistence.model.AgencyLocal
import com.lpirro.repository.mapper.util.MockMapperUtil
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RocketConfigurationMapperTest {

    lateinit var rocketMapper: RocketConfigurationMapper
    private val agencyMapper: AgencyMapper = mock()

    @Before
    fun setup() {
        rocketMapper = RocketConfigurationMapperImpl(agencyMapper)
        whenever(agencyMapper.mapToLocal(any())).thenReturn(MockMapperUtil.mockAgencyLocal())
        whenever(agencyMapper.mapToDomain(any())).thenReturn(MockMapperUtil.mockAgency())
    }

    @Test
    fun `RocketConfigurationLocal to RocketConfiguration maps Correctly`() {
        val configurationLocal = MockMapperUtil.mockRocketConfigurationLocal()
        val mappedConfiguration = rocketMapper.mapToDomain(configurationLocal)

        assertEquals(configurationLocal.id, mappedConfiguration.id)
        assertEquals(configurationLocal.name, mappedConfiguration.name)
        assertAgencyEquals(configurationLocal.manufacturer!!, mappedConfiguration.manufacturer!!)
        assertEquals(configurationLocal.variant, mappedConfiguration.variant)
        assertEquals(configurationLocal.height, mappedConfiguration.height)
        assertEquals(configurationLocal.diameter, mappedConfiguration.diameter)
        assertEquals(configurationLocal.gtoCapacity, mappedConfiguration.gtoCapacity)
        assertEquals(configurationLocal.leoCapacity, mappedConfiguration.leoCapacity)
        assertEquals(configurationLocal.toThrust, mappedConfiguration.toThrust)
        assertEquals(configurationLocal.apogee, mappedConfiguration.apogee)
        assertEquals(configurationLocal.reusable, mappedConfiguration.reusable)
        assertEquals(configurationLocal.successfulLaunches, mappedConfiguration.successfulLaunches)
        assertEquals(
            configurationLocal.consecutiveSuccessfulLaunches,
            mappedConfiguration.consecutiveSuccessfulLaunches
        )
        assertEquals(configurationLocal.failedLaunches, mappedConfiguration.failedLaunches)
        assertEquals(configurationLocal.pendingLaunches, mappedConfiguration.pendingLaunches)
        assertEquals(configurationLocal.launchCost, mappedConfiguration.launchCost)
        assertEquals(configurationLocal.infoUrl, mappedConfiguration.infoUrl)
        assertEquals(configurationLocal.wikiUrl, mappedConfiguration.wikiUrl)
        assertEquals(configurationLocal.minStage, mappedConfiguration.minStage)
        assertEquals(configurationLocal.maxStage, mappedConfiguration.maxStage)
        assertEquals(configurationLocal.description, mappedConfiguration.description)
        assertEquals(configurationLocal.imageUrl, mappedConfiguration.imageUrl)
    }

    @Test
    fun `RocketConfigurationRemote to RocketConfigurationLocal maps Correctly`() {
        val configurationRemote = MockMapperUtil.mockRocketConfigurationRemote()
        val mappedConfiguration = rocketMapper.mapToLocal(configurationRemote)

        assertEquals(configurationRemote.id, mappedConfiguration.id)
        assertEquals(configurationRemote.name, mappedConfiguration.name)
        assertAgencyEquals(configurationRemote.manufacturer!!, mappedConfiguration.manufacturer!!)
        assertEquals(configurationRemote.variant, mappedConfiguration.variant)
        assertEquals(configurationRemote.height, mappedConfiguration.height)
        assertEquals(configurationRemote.diameter, mappedConfiguration.diameter)
        assertEquals(configurationRemote.gtoCapacity, mappedConfiguration.gtoCapacity)
        assertEquals(configurationRemote.leoCapacity, mappedConfiguration.leoCapacity)
        assertEquals(configurationRemote.toThrust, mappedConfiguration.toThrust)
        assertEquals(configurationRemote.apogee, mappedConfiguration.apogee)
        assertEquals(configurationRemote.reusable, mappedConfiguration.reusable)
        assertEquals(configurationRemote.successfulLaunches, mappedConfiguration.successfulLaunches)
        assertEquals(
            configurationRemote.consecutiveSuccessfulLaunches,
            mappedConfiguration.consecutiveSuccessfulLaunches
        )
        assertEquals(configurationRemote.failedLaunches, mappedConfiguration.failedLaunches)
        assertEquals(configurationRemote.pendingLaunches, mappedConfiguration.pendingLaunches)
        assertEquals(configurationRemote.launchCost, mappedConfiguration.launchCost)
        assertEquals(configurationRemote.infoUrl, mappedConfiguration.infoUrl)
        assertEquals(configurationRemote.wikiUrl, mappedConfiguration.wikiUrl)
        assertEquals(configurationRemote.minStage, mappedConfiguration.minStage)
        assertEquals(configurationRemote.maxStage, mappedConfiguration.maxStage)
        assertEquals(configurationRemote.description, mappedConfiguration.description)
        assertEquals(configurationRemote.imageUrl, mappedConfiguration.imageUrl)
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
}
