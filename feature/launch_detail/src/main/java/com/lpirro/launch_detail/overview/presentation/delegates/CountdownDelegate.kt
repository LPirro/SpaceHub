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
import com.lpirro.core.extensions.visible
import com.lpirro.launch_detail.R
import com.lpirro.launch_detail.databinding.ItemCountdownHeaderBinding
import com.lpirro.launch_detail.overview.model.CountdownHeaderUi
import com.lpirro.launch_detail.overview.model.LaunchOverviewItem

fun countDownHeaderDelegate(
    addToCalendarListener: (launchName: String, launchDateMillis: Long) -> Unit,
    addToSavedListener: (launchId: String) -> Unit,
    removeFromSavedListener: (launchId: String) -> Unit
) =
    adapterDelegateViewBinding<CountdownHeaderUi, LaunchOverviewItem, ItemCountdownHeaderBinding>(
        { layoutInflater, root -> ItemCountdownHeaderBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.launchDate.text = item.launchDate

            if (item.isSaved) {
                binding.saveButton.setIconResource(R.drawable.heart_remove)
                binding.saveButton.text =
                    itemView.context.resources.getString(R.string.save_button_remove)
            } else {
                binding.saveButton.setIconResource(R.drawable.heart)
                binding.saveButton.text = itemView.context.resources.getString(R.string.save_button)
            }

            item.netMillis?.let { netMillis ->
                binding.launchCountdownView.visible = netMillis - System.currentTimeMillis() > 0
                binding.launchCountdownView.startCountdown(netMillis)
                binding.addToCalendarButton.setOnClickListener {
                    addToCalendarListener.invoke(item.name, netMillis)
                }
                binding.saveButton.setOnClickListener {
                    when (item.isSaved) {
                        true -> removeFromSavedListener.invoke(item.launchId)
                        false -> addToSavedListener.invoke(item.launchId)
                    }
                }
            }
        }
    }
