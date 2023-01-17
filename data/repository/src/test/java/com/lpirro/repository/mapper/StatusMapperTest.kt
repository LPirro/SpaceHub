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

class StatusMapperTest {

    lateinit var statusMapper: StatusMapper

    @Before
    fun setup() {
        statusMapper = StatusMapperImpl()
    }

    @Test
    fun `StatusLocal to Status maps Correctly`() {
        val statusLocal = MockMapperUtil.mockStatusLocal()
        val mappedStatus = statusMapper.mapToDomain(statusLocal)

        assertEquals(statusLocal.id, mappedStatus.id)
        assertEquals(statusLocal.name, mappedStatus.name)
        assertEquals(statusLocal.abbrev, mappedStatus.abbrev)
        assertEquals(statusLocal.description, mappedStatus.description)
    }

    @Test
    fun `StatusRemote to StatusLocal maps Correctly`() {
        val statusRemote = MockMapperUtil.mockStatusRemote()
        val mappedStatus = statusMapper.mapToLocal(statusRemote)

        assertEquals(statusRemote.id, mappedStatus.id)
        assertEquals(statusRemote.name, mappedStatus.name)
        assertEquals(statusRemote.abbrev, mappedStatus.abbrev)
        assertEquals(statusRemote.description, mappedStatus.description)
    }
}
