package com.lpirro.spacehub.core.util

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.TimeZone

class DateParserTest {

    private lateinit var dateUnderTest: String

    private lateinit var dateParser: DateParser

    @Before
    fun setup() {
        dateParser = DateParserImpl()
        dateUnderTest = "2022-12-25T09:30:00Z"
        TimeZone.setDefault(TimeZone.getTimeZone("CET"))
    }

    @Test
    fun `Full Date parsed correctly in dd MMM yyyy HHmm`() {
        val expectedResult = "25 Dec 2022 • 10:30"
        val result = dateParser.parseFullDate(dateUnderTest)

        assertEquals(result, expectedResult)
    }

    @Test
    fun `Full Date parsed correctly in dd MMM yyyy`() {
        val expectedResult = "25 Dec 2022"
        val result = dateParser.formatToDDMMMYYYY(dateUnderTest)

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

    @Test
    fun `Date pared correctly in formatToTimeAgo`() {
        val dateUnderTest = "2024-09-30T10:32:00Z"
        val result = dateParser.formatToTimeAgo(dateUnderTest)

        assertEquals(result, "1 hour ago")
    }
}
