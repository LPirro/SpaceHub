package com.lpirro.repository.mapper

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX"

class DateParserImpl : DateParser {

    private val locale: Locale = Locale.US

    override fun parseFullDate(dateString: String): String {
        return try {
            val parser = SimpleDateFormat(DATE_FORMAT, locale)
            val formatter = SimpleDateFormat("dd MMM yyyy • HH:mm", locale)
            parser.parse(dateString)?.let { formatter.format(it) } ?: "-"
        } catch (exception: ParseException) {
            "-"
        }
    }

    override fun parseDateDayMonth(dateString: String): String {
        return try {
            val parser = SimpleDateFormat(DATE_FORMAT, locale)
            val formatter = SimpleDateFormat("dd MMM", locale)
            parser.parse(dateString)?.let { formatter.format(it) } ?: "-"
        } catch (exception: ParseException) {
            "-"
        }
    }

    override fun parseDateInMillis(dateString: String): Long? {
        val parser = SimpleDateFormat(DATE_FORMAT, locale)
        val date: Date? = parser.parse(dateString)
        return date?.time
    }
}
