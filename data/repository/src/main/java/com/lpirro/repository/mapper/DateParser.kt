package com.lpirro.repository.mapper

interface DateParser {
    fun parseFullDate(dateString: String): String
    fun parseDateDayMonth(dateString: String): String
    fun parseDateInMillis(dateString: String): Long?
}
