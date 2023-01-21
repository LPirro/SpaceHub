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

package com.lpirro.launch_detail.tabs.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lpirro.launch_detail.mission.presentation.LaunchDetailMissionFragment
import com.lpirro.launch_detail.overview.presentation.LaunchDetailOverviewFragment
import com.lpirro.launch_detail.vehicles.presentation.LaunchDetailVehiclesFragment

private const val NUM_TABS = 3

class LaunchDetailViewPagerAdapter(
    private val launchId: String,
    fragmentActivity: FragmentActivity,
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return LaunchDetailOverviewFragment.newInstance(launchId)
            1 -> return LaunchDetailMissionFragment.newInstance(launchId)
            2 -> return LaunchDetailVehiclesFragment.newInstance(launchId)
        }
        return LaunchDetailOverviewFragment()
    }
}
