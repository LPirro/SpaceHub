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

package com.lpirro.launch_detail.mission.presentation.delegate

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.lpirro.core.util.glide.RemovePaddingTransformation
import com.lpirro.launch_detail.R
import com.lpirro.launch_detail.databinding.ItemMissionHeaderBinding
import com.lpirro.launch_detail.mission.model.LaunchMissionItem
import com.lpirro.launch_detail.mission.model.MissionHeaderUi

fun missionHeaderDelegate() =
    adapterDelegateViewBinding<MissionHeaderUi, LaunchMissionItem, ItemMissionHeaderBinding>({ layoutInflater, root ->
        ItemMissionHeaderBinding.inflate(layoutInflater, root, false)
    }) {
        bind {
            val resources = context.resources
            binding.missionName.text = item.name
            binding.missionOrbit.text = resources.getString(R.string.mission_header_orbit, item.orbit)
            binding.missionType.text = resources.getString(R.string.mission_header_type, item.missionType)
            binding.missionAgency.text = resources.getString(R.string.mission_header_agency, item.agencyName)

            Glide.with(itemView.context)
                .load(item.missionPatchUrl)
                .transform(RemovePaddingTransformation())
                .into(binding.missionPatchImage)
        }
    }
