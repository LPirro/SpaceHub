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

package com.lpirro.launch_detail.overview.presentation.delegates

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.lpirro.core.BuildConfig
import com.lpirro.launch_detail.databinding.ItemLocationBinding
import com.lpirro.launch_detail.overview.model.LaunchOverviewItem
import com.lpirro.launch_detail.overview.model.LocationUi

fun locationDelegate() =
    adapterDelegateViewBinding<LocationUi, LaunchOverviewItem, ItemLocationBinding>(
        { layoutInflater, root -> ItemLocationBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.mapsCardValue.text = item.name
            val latitude = item.latLng.latitude
            val longitude = item.latLng.longitude
            val zoom = 13
            val apiKey = BuildConfig.MAPS_API_KEY
            val mapUrl = "https://maps.googleapis.com/maps/api/staticmap?scale=2&center=$latitude,$longitude&zoom=$zoom&size=800x800&key=$apiKey"

            Glide.with(itemView.context)
                .load(mapUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(CenterCrop())
                .into(binding.googleMap)
        }
    }
