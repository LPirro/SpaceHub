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

import com.lpirro.repository.mapper.util.MockMapperUtil
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class OrbitMapperTest {

    lateinit var orbitMapper: OrbitMapper

    @Before
    fun setup() {
        orbitMapper = OrbitMapperImpl()
    }

    @Test
    fun `OrbitLocal to Orbit maps Correctly`() {
        val orbitLocal = MockMapperUtil.mockOrbitLocal()
        val mappedOrbit = orbitMapper.mapToDomain(orbitLocal)

        assertEquals(orbitLocal.id, mappedOrbit.id)
        assertEquals(orbitLocal.name, mappedOrbit.name)
        assertEquals(orbitLocal.abbrev, mappedOrbit.abbrev)
    }

    @Test
    fun `OrbitRemote to OrbitLocal maps Correctly`() {
        val orbitRemote = MockMapperUtil.mockOrbitRemote()
        val mappedOrbit = orbitMapper.mapToLocal(orbitRemote)

        assertEquals(orbitRemote.id, mappedOrbit.id)
        assertEquals(orbitRemote.name, mappedOrbit.name)
        assertEquals(orbitRemote.abbrev, mappedOrbit.abbrev)
    }
}
