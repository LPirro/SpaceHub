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

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.lpirro.core.navigation.NavigationUtil

class NotificationAlarmReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        createNotificationChannel(context)

        val launchId = intent?.getStringExtra(LAUNCH_ID_KEY)
        val notificationTitle = intent?.getStringExtra(NOTIFICATION_TITLE_KEY)
        val notificationMessage = intent?.getStringExtra(NOTIFICATION_MESSAGE_KEY)

        val taskBuilder = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(
                Intent(Intent.ACTION_VIEW).apply {
                    data = launchId?.let { NavigationUtil.getLaunchDetailUri(it) }
                }
            )
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(context, LAUNCH_UPDATES_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle(notificationTitle)
            .setColor(ContextCompat.getColor(context, com.lpirro.core.R.color.seed))
            .setContentText(notificationMessage)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(taskBuilder)
            .build()

        notification.flags = Notification.FLAG_AUTO_CANCEL
        NotificationManagerCompat.from(context).notify(launchId.hashCode(), notification)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.launch_remiders_notificaton_channel_name)
            val descriptionText = context.getString(R.string.launch_remiders_notificaton_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(LAUNCH_UPDATES_CHANNEL_ID, name, importance)
            channel.description = descriptionText
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val LAUNCH_ID_KEY = "launch_id"
        const val NOTIFICATION_TITLE_KEY = "notification_title"
        const val NOTIFICATION_MESSAGE_KEY = "notification_message"

        private const val LAUNCH_UPDATES_CHANNEL_ID = "launch_updates_channel_id"
    }
}
