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

package com.spacehub.launchdetail.presentation.overview.mapper

import com.spacehub.launchdetail.BuildConfig

private const val GOOGLE_MAPS_URL = "https://maps.googleapis.com/maps/api/staticmap?"

internal class GoogleMapsImageUrlMapperImpl() : GoogleMapsImageUrlMapper {
    override fun map(latitude: Double, longitude: Double): String {
        val zoom = 12
        val apiKey = BuildConfig.MAPS_API_KEY
        return "${GOOGLE_MAPS_URL}scale=2&center=$latitude,$longitude&zoom=$zoom&size=800x800&key=$apiKey"
    }
}

interface GoogleMapsImageUrlMapper {
    fun map(latitude: Double, longitude: Double): String
}