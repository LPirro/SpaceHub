package com.lpirro.spacehub.core.util

import org.joda.time.format.DateTimeFormat
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
    fun `Date pared correctly in formatToTimeAgo when the time now`() {
        val dateUnderTest = "2024-09-30T10:32:00Z"

        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val currentDateMillis = inputFormat.parseLocalDateTime("2024-09-30T10:32:00Z").toDateTime().millis
        val result = dateParser.formatToTimeAgo(dateUnderTest, currentDateMillis)

        assertEquals(result, "Just now")
    }

    @Test
    fun `Date pared correctly in formatToTimeAgo when the time is 1 hour ago`() {
        val dateUnderTest = "2024-09-30T10:32:00Z"

        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val currentDateMillis = inputFormat.parseLocalDateTime("2024-09-30T11:32:00Z").toDateTime().millis
        val result = dateParser.formatToTimeAgo(dateUnderTest, currentDateMillis)

        assertEquals(result, "1 hour ago")
    }

    @Test
    fun `Date pared correctly in formatToTimeAgo when the time is 3 hour ago`() {
        val dateUnderTest = "2024-09-30T10:32:00Z"

        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val currentDateMillis = inputFormat.parseLocalDateTime("2024-09-30T13:32:00Z").toDateTime().millis
        val result = dateParser.formatToTimeAgo(dateUnderTest, currentDateMillis)

        assertEquals(result, "3 hours ago")
    }

    @Test
    fun `Date pared correctly in formatToTimeAgo when the time is 1 minute ago`() {
        val dateUnderTest = "2024-09-30T10:32:00Z"

        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val currentDateMillis = inputFormat.parseLocalDateTime("2024-09-30T10:33:00Z").toDateTime().millis
        val result = dateParser.formatToTimeAgo(dateUnderTest, currentDateMillis)

        assertEquals(result, "1 minute ago")
    }

    @Test
    fun `Date pared correctly in formatToTimeAgo when the time is 15 minutes ago`() {
        val dateUnderTest = "2024-09-30T10:32:00Z"

        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val currentDateMillis = inputFormat.parseLocalDateTime("2024-09-30T10:47:00Z").toDateTime().millis
        val result = dateParser.formatToTimeAgo(dateUnderTest, currentDateMillis)

        assertEquals(result, "15 minutes ago")
    }

    @Test
    fun `Date pared correctly in formatToTimeAgo when the time is yesterday`() {
        val dateUnderTest = "2024-09-30T10:32:00Z"

        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val currentDateMillis = inputFormat.parseLocalDateTime("2024-10-01T11:32:00Z").toDateTime().millis
        val result = dateParser.formatToTimeAgo(dateUnderTest, currentDateMillis)

        assertEquals(result, "Yesterday")
    }

    @Test
    fun `Date pared correctly in formatToTimeAgo when the time 3 days ago`() {
        val dateUnderTest = "2024-09-30T10:32:00Z"

        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val currentDateMillis = inputFormat.parseLocalDateTime("2024-10-03T11:32:00Z").toDateTime().millis
        val result = dateParser.formatToTimeAgo(dateUnderTest, currentDateMillis)

        assertEquals(result, "3 days ago")
    }

    @Test
    fun `Date pared correctly in formatToTimeAgo when the time 1 month ago`() {
        val dateUnderTest = "2024-09-30T10:32:00Z"

        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val currentDateMillis = inputFormat.parseLocalDateTime("2024-10-30T11:32:00Z").toDateTime().millis
        val result = dateParser.formatToTimeAgo(dateUnderTest, currentDateMillis)

        assertEquals(result, "1 month ago")
    }

    @Test
    fun `Date pared correctly in formatToTimeAgo when the time 3 months ago`() {
        val dateUnderTest = "2024-09-30T10:32:00Z"

        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val currentDateMillis = inputFormat.parseLocalDateTime("2024-12-30T11:32:00Z").toDateTime().millis
        val result = dateParser.formatToTimeAgo(dateUnderTest, currentDateMillis)

        assertEquals(result, "3 months ago")
    }

    @Test
    fun `Date pared correctly in formatToTimeAgo when the time 1 year ago`() {
        val dateUnderTest = "2024-09-30T10:32:00Z"

        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val currentDateMillis = inputFormat.parseLocalDateTime("2025-12-30T11:32:00Z").toDateTime().millis
        val result = dateParser.formatToTimeAgo(dateUnderTest, currentDateMillis)

        assertEquals(result, "1 year ago")
    }

    @Test
    fun `Date pared correctly in formatToTimeAgo when the time 5 years ago`() {
        val dateUnderTest = "2024-09-30T10:32:00Z"

        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val currentDateMillis = inputFormat.parseLocalDateTime("2029-12-30T11:32:00Z").toDateTime().millis
        val result = dateParser.formatToTimeAgo(dateUnderTest, currentDateMillis)

        assertEquals(result, "5 years ago")
    }
}
