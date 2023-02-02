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

package com.lpirro.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import org.joda.time.DateTime

class LaunchNotificationSchedulerImpl(private val application: Application) : LaunchNotificationScheduler {

    override fun createNotificationAlarm(
        timeMillis: Long,
        launchId: String,
        notificationTitle: String
    ) {
        val requestCode = launchId.hashCode()

        val alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(application, NotificationAlarmReceiver::class.java)
        intent.putExtra(NotificationAlarmReceiver.LAUNCH_ID_KEY, launchId)
        intent.putExtra(NotificationAlarmReceiver.NOTIFICATION_TITLE_KEY, notificationTitle)
        intent.putExtra(NotificationAlarmReceiver.NOTIFICATION_MESSAGE_KEY, "Is Launching in 10 minutes") // TODO Remove hardcoded string as part of SH-57

        val pendingIntent = PendingIntent.getBroadcast(
            application,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val timeTarget = DateTime(timeMillis).minusMinutes(10).millis
        val clockInfo = AlarmManager.AlarmClockInfo(timeTarget, pendingIntent)
        alarmManager.setAlarmClock(clockInfo, pendingIntent)
    }

    override fun removeNotificationAlarm(launchId: String) {
        val requestCode = launchId.hashCode()

        val alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(application, NotificationAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            application,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}
