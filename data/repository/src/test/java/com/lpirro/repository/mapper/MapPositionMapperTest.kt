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

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MapPositionMapperTest {

    lateinit var mapPositionMapper: MapPositionMapper

    @Before
    fun setup() {
        mapPositionMapper = MapPositionMapperImpl()
    }

    @Test
    fun `mapPositionMapper maps correctly`() {
        val mappedResult = mapPositionMapper.mapToDomain("150", "200.0")
        assertEquals(150.0, mappedResult!!.latitude, 0.0)
        assertEquals(200.0, mappedResult.longitude, 0.0)
    }

    @Test
    fun `mapPositionMapper null latitude returns null as result of the mapping`() {
        val mappedResult = mapPositionMapper.mapToDomain(null, "200.0")
        assertEquals(mappedResult, null)
    }

    @Test
    fun `mapPositionMapper null longitude returns null as result of the mapping`() {
        val mappedResult = mapPositionMapper.mapToDomain("200.0", null)
        assertEquals(mappedResult, null)
    }

    @Test
    fun `mapPositionMapper null longitude and null latitude returns null as result of the mapping`() {
        val mappedResult = mapPositionMapper.mapToDomain(null, null)
        assertEquals(mappedResult, null)
    }
}
