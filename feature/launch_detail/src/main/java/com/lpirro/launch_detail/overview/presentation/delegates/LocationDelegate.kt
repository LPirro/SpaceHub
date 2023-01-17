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

package com.lpirro.launch_detail.overview.presentation.delegates

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.lpirro.launch_detail.databinding.ItemLocationBinding
import com.lpirro.launch_detail.overview.model.LaunchOverviewItem
import com.lpirro.launch_detail.overview.model.LocationUi

fun locationDelegate() =
    adapterDelegateViewBinding<LocationUi, LaunchOverviewItem, ItemLocationBinding>(
        { layoutInflater, root -> ItemLocationBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.mapsCardValue.text = item.name
            binding.googleMap.onCreate(null)
            binding.googleMap.onResume()
            binding.googleMap.getMapAsync { map ->
                map.addMarker(MarkerOptions().position(item.latLng))
                map.moveCamera(CameraUpdateFactory.newLatLng(item.latLng))
                map.uiSettings.isMapToolbarEnabled = false
            }
        }
    }
