/*
 *
 *  * SpaceHub - Designed and Developed by LPirro (Leonardo Pirro)
 *  * Copyright (C) 2023 Leonardo Pirro
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.lpirro.spacehub.core.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun CountdownTimer(days: Int, hours: Int, minutes: Int, seconds: Int) {
    var days by remember { mutableIntStateOf(days) }
    var hours by remember { mutableIntStateOf(hours) }
    var minutes by remember { mutableIntStateOf(minutes) }
    var seconds by remember { mutableIntStateOf(seconds) }

    // Countdown logic (mock behavior)
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            seconds--
            if (seconds < 0) {
                seconds = 59
                minutes--
            }
            if (minutes < 0) {
                minutes = 59
                hours--
            }
            if (hours < 0) {
                hours = 23
                days--
            }
        }
    }
    Row(horizontalArrangement = Arrangement.Center) {
        if (days > 0) {
            TimeUnitBox(value = days, label = "Days")
            TimeSeparator()
        }
        TimeUnitBox(value = hours, label = "Hours")
        TimeSeparator()
        TimeUnitBox(value = minutes, label = "Mins")
        TimeSeparator()
        TimeUnitBox(value = seconds, label = "Sec")
    }
}

@Composable
fun TimeUnitBox(value: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString().padStart(2, '0'),
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            ),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun TimeSeparator() {
    Text(
        text = ":",
        modifier = Modifier.padding(horizontal = 6.dp),
        style = TextStyle(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCountdownSection() {
    CountdownTimer(
        days = 10,
        hours = 13,
        minutes = 11,
        seconds = 22,
    )
}
