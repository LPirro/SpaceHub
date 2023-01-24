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

package com.lpirro.core.ui.view

import android.os.CountDownTimer
import android.widget.TextView

class CountdownTimerTextView(
    startDateMillis: Long,
    endDateMillis: Long,
    private val textView: TextView
) : CountDownTimer(startDateMillis - endDateMillis, 1000L) {

    override fun onTick(millisUntilFinished: Long) {
        val seconds = (millisUntilFinished) / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val time = buildString {
            append("In ")
            if (days != 0L) appendDays(days)
            appendHours(hours)
            appendMinutes(minutes)
            if (days == 0L) appendSeconds(seconds)
        }
        textView.text = time
    }

    override fun onFinish() {
        this.cancel()
        textView.text = ""
    }

    private fun StringBuilder.appendDays(days: Long): StringBuilder {
        append(days)
        return if (days > 1L) append(" Days ") else append(" Day ")
    }

    private fun StringBuilder.appendMinutes(minutes: Long): StringBuilder {
        return append(minutes % 60).append(" Min ")
    }

    private fun StringBuilder.appendHours(hours: Long): StringBuilder {
        val hoursFormatted = hours.mod(24)
        append(hoursFormatted)
        return if (hoursFormatted > 1L) append(" Hours ") else append(" Hour ")
    }

    private fun StringBuilder.appendSeconds(seconds: Long): StringBuilder {
        return append(seconds % 60).append(" Seconds")
    }
}
