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
package com.lpirro.spacehub.core.util

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

const val FULL_DATE_INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
private const val LAUNCH_FULL_DATE_OUTPUT_FORMAT = "dd MMM yyyy • HH:mm"
private const val DATE_DD_MMM_FORMAT = "dd MMM"
private const val DATE_DD_MMM_YYYY_FORMAT = "dd MMM yyyy"

private const val SECOND = 1
private const val MINUTE = 60 * SECOND
private const val HOUR = 60 * MINUTE
private const val DAY = 24 * HOUR
private const val MONTH = 30 * DAY
private const val YEAR = 12 * MONTH

interface DateParser {
    fun parseFullDate(dateString: String): String

    fun parseDateDayMonth(dateString: String): String

    fun parseDateInMillis(dateString: String): Long?

    fun formatToTimeAgo(dateString: String, currentDateInMillis: Long = DateTime.now().millis): String?

    fun formatToDDMMMYYYY(dateString: String): String?
}

class DateParserImpl : DateParser {
    override fun parseFullDate(dateString: String): String = try {
        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val outputFormat = DateTimeFormat.forPattern(LAUNCH_FULL_DATE_OUTPUT_FORMAT)
        val parser = inputFormat.parseDateTime(dateString)
        outputFormat.print(parser)
    } catch (exception: IllegalArgumentException) {
        "-"
    }

    override fun parseDateDayMonth(dateString: String): String = try {
        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val outputFormat = DateTimeFormat.forPattern(DATE_DD_MMM_FORMAT)
        val parser = inputFormat.parseDateTime(dateString)
        outputFormat.print(parser)
    } catch (exception: IllegalArgumentException) {
        "-"
    }

    override fun parseDateInMillis(dateString: String): Long? = try {
        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val parser = inputFormat.parseDateTime(dateString)
        parser.millis
    } catch (exception: IllegalArgumentException) {
        null
    }

    override fun formatToTimeAgo(dateString: String, currentDateInMillis: Long): String? {
        try {
            val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
            val dateInMillis = inputFormat.parseLocalDateTime(dateString).toDateTime().millis

            val diff = (currentDateInMillis - dateInMillis) / 1000

            return when {
                diff < MINUTE -> "Just now"
                diff < 2 * MINUTE -> "1 minute ago"
                diff < 60 * MINUTE -> "${diff / MINUTE} minutes ago"
                diff < 2 * HOUR -> "1 hour ago"
                diff < 24 * HOUR -> "${diff / HOUR} hours ago"
                diff < 2 * DAY -> "Yesterday"
                diff < 30 * DAY -> "${diff / DAY} days ago"
                diff < 2 * MONTH -> "1 month ago"
                diff < 12 * MONTH -> "${diff / MONTH} months ago"
                diff < 2 * YEAR -> "1 year ago"
                else -> "${diff / YEAR} years ago"
            }
        } catch (exception: IllegalArgumentException) {
            return null
        }
    }

    override fun formatToDDMMMYYYY(dateString: String): String = try {
        val inputFormat = DateTimeFormat.forPattern(FULL_DATE_INPUT_FORMAT)
        val outputFormat = DateTimeFormat.forPattern(DATE_DD_MMM_YYYY_FORMAT)
        val parser = inputFormat.parseDateTime(dateString)
        outputFormat.print(parser)
    } catch (exception: IllegalArgumentException) {
        "-"
    }
}
