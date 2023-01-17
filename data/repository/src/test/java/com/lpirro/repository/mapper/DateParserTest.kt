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
import java.text.SimpleDateFormat
import java.util.Locale

private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX"

class DateParserTest {

    private lateinit var dateUnderTest: String

    private lateinit var dateParser: DateParser

    @Before
    fun setup() {
        dateParser = DateParserImpl()
        dateUnderTest = "2022-12-25T09:30:00Z"
    }

    @Test
    fun `Full Date parsed correctly in dd MMM yyyy HHmm`() {
        val parser = SimpleDateFormat(DATE_FORMAT, Locale.US)
        val formatter = SimpleDateFormat("dd MMM yyyy • HH:mm", Locale.US)

        val expectedResult = parser.parse(dateUnderTest)?.let { formatter.format(it) } ?: "-"
        val result = dateParser.parseFullDate(dateUnderTest)

        assertEquals(result, expectedResult)
    }

    @Test
    fun `Date Day Month parsed correctly in dd MMM`() {
        val expectedResult = "25 Dec"
        val result = dateParser.parseDateDayMonth(dateUnderTest)

        assertEquals(result, expectedResult)
    }

    @Test
    fun `Date parsed correctly in milliseconds`() {
        val expectedResult = 1671960600000
        val result = dateParser.parseDateInMillis(dateUnderTest)

        assertEquals(result, expectedResult)
    }

    @Test
    fun `Unparselable Full Date returns -`() {
        val dateUnderTest = "2022-12-20Z"
        val result = dateParser.parseFullDate(dateUnderTest)

        assertEquals(result, "-")
    }

    @Test
    fun `Unparselable Date Day Month returns -`() {
        val dateUnderTest = "2022-12-20Z"
        val result = dateParser.parseDateDayMonth(dateUnderTest)

        assertEquals(result, "-")
    }
}
