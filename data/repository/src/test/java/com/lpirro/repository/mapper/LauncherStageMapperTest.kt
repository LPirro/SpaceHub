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

import com.lpirro.domain.models.LauncherLanding
import com.lpirro.network.models.LauncherLandingRemote
import com.lpirro.persistence.model.LauncherLandingLocal
import com.lpirro.repository.mapper.util.MockMapperUtil
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LauncherStageMapperTest {

    lateinit var launcherStageMapper: LauncherStageMapper
    private val launcherLandingMapper: LauncherLandingMapper = mock()

    @Before
    fun setup() {
        launcherStageMapper = LauncherStageMapperImpl(launcherLandingMapper)

        whenever(launcherLandingMapper.mapToDomain(any())).thenReturn(MockMapperUtil.mockLauncherLanding())
        whenever(launcherLandingMapper.mapToLocal(any())).thenReturn(MockMapperUtil.mockLauncherLandingLocal())
    }

    @Test
    fun `LauncherStageLocal to LauncherStage maps Correctly`() {
        val launcherStage = MockMapperUtil.mockLauncherStageLocal()
        val mappedLauncherStage = launcherStageMapper.mapToDomain(launcherStage)

        assertEquals(launcherStage.type, mappedLauncherStage.type)
        assertEquals(launcherStage.serialNumber, mappedLauncherStage.serialNumber)
        assertLauncherLanding(launcherStage.landing!!, mappedLauncherStage.landing!!)
    }

    @Test
    fun `LauncherStageRemote to LauncherStageLocal maps Correctly`() {
        val launcherStage = MockMapperUtil.mockLauncherStageRemote()
        val mappedLauncherStage = launcherStageMapper.mapToLocal(launcherStage)

        assertEquals(launcherStage.type, mappedLauncherStage.type)
        assertEquals(launcherStage.launcher.serialNumber, mappedLauncherStage.serialNumber)
        assertLauncherLanding(launcherStage.launcherLanding!!, mappedLauncherStage.landing!!)
    }

    private fun assertLauncherLanding(
        launcherLandingLocal: LauncherLandingLocal,
        launcherLanding: LauncherLanding
    ) {
        assertEquals(launcherLandingLocal.type, launcherLanding.type)
        assertEquals(launcherLandingLocal.locationName, launcherLanding.locationName)
    }

    private fun assertLauncherLanding(
        launcherLandingRemote: LauncherLandingRemote,
        launcherLandingLocal: LauncherLandingLocal
    ) {
        assertEquals(launcherLandingRemote.type.abbrev, launcherLandingLocal.type)
        assertEquals(launcherLandingRemote.landingLocation.name, launcherLandingLocal.locationName)
    }
}
