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

import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private const val LAUNCH_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX"
private const val NEWS_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.000X"
private const val FULL_DATE_OUTPUT_PATTERN = "dd MMM yyyy • HH:mm"
private const val DAY_MONTH_DATE_OUTPUT_PATTERN = "dd MMM"

class DateParserImpl : DateParser {

    private val locale: Locale = Locale.US

    override fun parseFullDate(dateString: String): String {
        return try {
            val parser = SimpleDateFormat(LAUNCH_DATE_FORMAT, locale)
            val formatter = SimpleDateFormat(FULL_DATE_OUTPUT_PATTERN, locale)
            parser.parse(dateString)?.let { formatter.format(it) } ?: "-"
        } catch (exception: ParseException) {
            "-"
        }
    }

    override fun parseDateDayMonth(dateString: String): String {
        return try {
            val parser = SimpleDateFormat(LAUNCH_DATE_FORMAT, locale)
            val formatter = SimpleDateFormat(DAY_MONTH_DATE_OUTPUT_PATTERN, locale)
            parser.parse(dateString)?.let { formatter.format(it) } ?: "-"
        } catch (exception: ParseException) {
            "-"
        }
    }

    override fun parseDateInMillis(dateString: String): Long? {
        val parser = SimpleDateFormat(LAUNCH_DATE_FORMAT, locale)
        val date: Date? = parser.parse(dateString)
        return date?.time
    }

    override fun formatToTimeAgo(dateString: String): String? {
        return try {
            val inputFormat = SimpleDateFormat(NEWS_DATE_FORMAT, locale)
            val date: Date = inputFormat.parse(dateString)!!

            DateUtils.getRelativeTimeSpanString(
                date.time,
                Calendar.getInstance().timeInMillis,
                DateUtils.MINUTE_IN_MILLIS
            ).toString()
        } catch (exception: Exception) {
            null
        }
    }
}
