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

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.lpirro.core.extensions.hide
import com.lpirro.core.extensions.visible
import com.lpirro.domain.models.Status
import com.lpirro.launch_detail.R
import com.lpirro.launch_detail.databinding.ItemPastLaunchHeaderBinding
import com.lpirro.launch_detail.overview.model.LaunchOverviewItem
import com.lpirro.launch_detail.overview.model.PastLaunchHeaderUi

fun pastLaunchHeaderDelegate(
    removeFromSavedListener: (launchId: String) -> Unit,
) =
    adapterDelegateViewBinding<PastLaunchHeaderUi, LaunchOverviewItem, ItemPastLaunchHeaderBinding>(
        { layoutInflater, root -> ItemPastLaunchHeaderBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            val resources = itemView.context.resources

            val launchStatus = when (item.status) {
                is Status.Success -> resources.getString(R.string.launch_completed)
                else -> resources.getString(R.string.launch_failed)
            }

            val launchIcon = when (item.status) {
                is Status.Success -> com.lpirro.core.R.drawable.outline_done
                else -> com.lpirro.core.R.drawable.outline_error
            }

            val statusText = resources.getString(R.string.past_launch_status, launchStatus, item.launchDate)

            binding.status.icon = launchIcon
            binding.status.setStatus(item.status, statusText)
            binding.headerDescription.text = item.status.description
            binding.saveButton.visible = item.isSaved
            binding.saveButton.setOnClickListener {
                it.hide()
                removeFromSavedListener.invoke(item.launchId)
            }
        }
    }
