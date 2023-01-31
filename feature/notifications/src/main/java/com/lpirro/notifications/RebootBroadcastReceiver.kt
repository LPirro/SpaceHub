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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.lpirro.domain.usecase.GetSavedLaunchesUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RebootBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var savedLaunchesUseCase: GetSavedLaunchesUseCase

    @Inject
    lateinit var launchNotificationScheduler: LaunchNotificationScheduler

    override fun onReceive(context: Context?, intent: Intent) {

        when (intent.action) {
            Intent.ACTION_MY_PACKAGE_REPLACED, Intent.ACTION_BOOT_COMPLETED -> {
                rescheduleAlarms()
            }
        }
    }

    private fun rescheduleAlarms() {
        CoroutineScope(Dispatchers.Main).launch {
            savedLaunchesUseCase().single().forEach { launch ->
                launchNotificationScheduler.createNotificationAlarm(
                    timeMillis = launch.netMillis!!,
                    launchId = launch.id,
                    notificationTitle = launch.name
                )
            }
        }
    }
}
