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

package com.spacehub.launchdetail.presentation.overview

import com.spacehub.launchdetail.BuildConfig

object MockData {

    private val latitude = 21.283333
    private val longitude = 112.0
    private val zoom = 7
    private val apiKey = BuildConfig.MAPS_API_KEY

    val mapUrl = "https://maps.googleapis.com/maps/api/staticmap?scale=2&center=$latitude,$longitude&zoom=$zoom&size=800x800&key=$apiKey"

    val lunchpadItems = listOf(
        "Name" to "Space Launch Complex 40",
        "Location" to "Cape Canaveral, FL, USA",
        "Total Launches" to "162",
    )

    val agencyItems = listOf(
        "Name" to "SpaceX",
        "Country" to "USA \uD83C\uDDFA\uD83C\uDDF8",
        "Administrator" to "CEO: Elon Musk",
        "Founded" to "2002",
        "Total Launches" to "211",
    )

    val agencyLogoUrl = "https://thespacedevs-prod.nyc3.digitaloceanspaces.com/media/images/spacex_logo_20220826094919.png"
}
