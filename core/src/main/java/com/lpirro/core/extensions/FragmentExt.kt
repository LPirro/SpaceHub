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

package com.lpirro.core.extensions

import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment

fun Fragment.launchGoogleMapsIntent(mapUrl: String) {
    val gmmIntentUri = Uri.parse(mapUrl)
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    startActivity(mapIntent)
}

fun Fragment.launchChromeCustomTab(url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
}

fun Fragment.launchAddToCalendarIntent(title: String, dateMillis: Long) {
    val intent = Intent(Intent.ACTION_EDIT)
    intent.type = "vnd.android.cursor.item/event"
    intent.putExtra(CalendarContract.Events.TITLE, title)
    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, dateMillis)
    intent.putExtra(CalendarContract.Events.ALL_DAY, false)
    startActivity(intent)
}
