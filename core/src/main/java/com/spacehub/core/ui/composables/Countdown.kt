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

package com.spacehub.core.ui.composables

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lpirro.spacehub.core.R
import java.util.concurrent.TimeUnit

@Composable
fun CountdownTimer(targetDateMillis: Long) {
    var days by remember { mutableLongStateOf(0L) }
    var hours by remember { mutableLongStateOf(0L) }
    var minutes by remember { mutableLongStateOf(0L) }
    var seconds by remember { mutableLongStateOf(0L) }

    // Countdown logic (mock behavior)
    LaunchedEffect(targetDateMillis) {
        val currentMillis = System.currentTimeMillis()
        val remainingMillis = targetDateMillis - currentMillis

        object : CountDownTimer(remainingMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val timeDifference = targetDateMillis - System.currentTimeMillis()
                days = TimeUnit.MILLISECONDS.toDays(timeDifference)
                hours = TimeUnit.MILLISECONDS.toHours(timeDifference) % 24
                minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference) % 60
                seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifference) % 60
            }

            override fun onFinish() {
                this.cancel()
            }
        }.start()
    }

    Row(horizontalArrangement = Arrangement.Center) {
        if (days > 0) {
            TimeUnitBox(
                value = days.toInt(),
                label = pluralStringResource(R.plurals.days, 2),
            )
            TimeSeparator()
        }
        TimeUnitBox(
            value = hours.toInt(),
            label = pluralStringResource(R.plurals.hours, 2),
        )
        TimeSeparator()
        TimeUnitBox(
            value = minutes.toInt(),
            label = pluralStringResource(R.plurals.minutes, 2),
        )
        TimeSeparator()
        TimeUnitBox(
            value = seconds.toInt(),
            label = pluralStringResource(R.plurals.seconds, 2),
        )
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
        targetDateMillis = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1),
    )
}
