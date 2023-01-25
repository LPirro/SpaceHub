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

import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.lpirro.core.extensions.visible
import com.lpirro.core.ui.recyclerview.decorator.VerticalSpaceItemDecoration
import com.lpirro.launch_detail.databinding.ItemMissionUpdatesBinding
import com.lpirro.launch_detail.mission.model.LaunchMissionItem
import com.lpirro.launch_detail.mission.model.UpdatesUi
import com.lpirro.launch_detail.mission.presentation.delegate.adapter.UpdateAdapter

fun missionUpdatesDelegate() =
    adapterDelegateViewBinding<UpdatesUi, LaunchMissionItem, ItemMissionUpdatesBinding>({ layoutInflater, root ->
        ItemMissionUpdatesBinding.inflate(layoutInflater, root, false)
    }) {
        bind {
            val edgeSpacing = itemView.context.resources.getDimensionPixelSize(com.lpirro.core.R.dimen.margin_16dp)
            val spacing = itemView.context.resources.getDimensionPixelSize(com.lpirro.core.R.dimen.margin_12dp)
            val updateAdapter = UpdateAdapter()
            binding.missionUpdatesRecyclerView.adapter = updateAdapter
            binding.missionUpdatesRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            binding.missionUpdatesRecyclerView.addItemDecoration(VerticalSpaceItemDecoration(edgeSpacing = edgeSpacing, spaceSize = spacing))
            updateAdapter.submitList(item.updates)
            binding.noUpdatesLabel.visible = item.updates.isEmpty()
            binding.missionUpdatesRecyclerView.visible = item.updates.isNotEmpty()
        }
    }
