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

package com.lpirro.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.lpirro.core.base.BaseFragment
import com.lpirro.launches.databinding.FragmentLaunchesBinding

class LaunchesTabFragment : BaseFragment<FragmentLaunchesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLaunchesBinding
        get() = FragmentLaunchesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabs()
    }

    private fun setupTabs() {
        val adapter = LaunchesViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = resources.getString(R.string.tab_launches_upcoming)
                    tab.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.selector_calendar_clock
                    )
                }
                1 -> {
                    tab.text = resources.getString(R.string.tab_launches_past)
                    tab.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.selector_rocket
                    )
                }
            }
        }.attach()
    }
}
