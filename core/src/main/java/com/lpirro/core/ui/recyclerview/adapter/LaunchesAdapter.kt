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

package com.lpirro.core.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.lpirro.core.R
import com.lpirro.core.databinding.ItemLaunchBinding
import com.lpirro.core.ui.view.CountdownTimerTextView
import com.lpirro.domain.models.Launch
import com.lpirro.domain.models.Status

class LaunchesAdapter(
    private val launchClick: (launchId: String) -> Unit,
    private val launchStatusClick: (status: Status) -> Unit,
) :
    ListAdapter<Launch, LaunchesAdapter.LaunchesViewHolder>(LaunchDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchesViewHolder {
        val binding = ItemLaunchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LaunchesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaunchesViewHolder, position: Int) {
        val launch = getItem(position)

        holder.binding.launchTitle.text = launch.name
        holder.binding.agencyName.text = launch.launchServiceProvider.name
        holder.binding.location.text = launch.pad.location.name
        holder.binding.date.text = launch.netDisplay
        holder.binding.status.setStatus(launch.status)

        holder.binding.status.setOnClickListener { launchStatusClick.invoke(launch.status) }
        holder.itemView.setOnClickListener { launchClick.invoke(launch.id) }

        Glide.with(holder.itemView.context)
            .load(launch.image)
            .placeholder(R.drawable.launch_image_placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(CenterCrop())
            .into(holder.binding.launchImage)

        holder.countdownTimerTextView?.cancel()

        holder.countdownTimerTextView =
            CountdownTimerTextView(launch.netMillis!!, System.currentTimeMillis(), holder.binding.countdownText) // TODO remove evils operator !!
        holder.countdownTimerTextView?.start()
    }

    inner class LaunchesViewHolder(val binding: ItemLaunchBinding) : RecyclerView.ViewHolder(binding.root) {
        var countdownTimerTextView: CountdownTimerTextView? = null
    }
}
