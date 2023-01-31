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

package com.lpirro.launch_detail.vehicles.presentation.delegate

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.lpirro.core.extensions.getColorFromAttr
import com.lpirro.core.util.glide.RemovePaddingTransformation
import com.lpirro.launch_detail.R
import com.lpirro.launch_detail.databinding.ItemRocketBinding
import com.lpirro.launch_detail.vehicles.model.LaunchVehiclesItem
import com.lpirro.launch_detail.vehicles.model.RocketUi

fun rocketDelegate() =
    adapterDelegateViewBinding<RocketUi, LaunchVehiclesItem, ItemRocketBinding>({ layoutInflater, root ->
        ItemRocketBinding.inflate(layoutInflater, root, false)
    }) {
        bind {
            val context = itemView.context
            binding.rocketName.text = item.name
            binding.manufacturerValue.text = item.manufacturer
            binding.variantValue.text = item.variant
            binding.heightValue.text = item.height
            binding.diameterValue.text = item.diameter
            binding.leoCapacityValue.text = item.leoCapacity
            binding.gtoCapacityValue.text = item.gtoCapacity
            binding.thrustValue.text = item.toThrust
            binding.apogeeValue.text = item.apogee
            binding.successfulLaunchesValue.text = item.successfulLaunches
            binding.consecutiveSuccessfulLaunchesValue.text = item.consecutiveSuccessfulLaunches
            binding.failedLaunchesValue.text = item.failedLaunches
            binding.costPerLaunchValue.text = item.launchCost

            Glide.with(itemView.context)
                .load(item.imageUrl)
                .transform(RemovePaddingTransformation())
                .into(binding.rocketImage)

            when (item.reusable) {
                true -> {
                    binding.reusableIcon.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.round_check_circle
                        )
                    )
                    binding.reusableIcon.setColorFilter(
                        context.getColorFromAttr(com.google.android.material.R.attr.colorPrimary),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }
                false -> {
                    binding.reusableIcon.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.round_error_circle
                        )
                    )
                    binding.reusableIcon.setColorFilter(
                        context.getColorFromAttr(com.google.android.material.R.attr.colorError),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }
            }
        }
    }
