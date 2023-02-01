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

package com.lpirro.launch_detail.tabs.presentation

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.lpirro.core.base.BaseFragment
import com.lpirro.domain.models.Launch
import com.lpirro.launch_detail.R
import com.lpirro.launch_detail.databinding.FragmentLaunchDetailBinding
import com.lpirro.launch_detail.tabs.viewmodel.LaunchDetailUiState
import com.lpirro.launch_detail.tabs.viewmodel.LaunchDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LaunchDetailFragment : BaseFragment<FragmentLaunchDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLaunchDetailBinding
        get() = FragmentLaunchDetailBinding::inflate

    private val args: LaunchDetailFragmentArgs by navArgs()
    private val viewModel: LaunchDetailViewModel by viewModels()

    private lateinit var adapter: LaunchDetailViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(com.lpirro.core.R.transition.slide_right)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObservers()
        createTabs()
        viewModel.getLaunch(args.launchId)
    }

    private fun registerObservers() {
        lifecycleScope.launchWhenCreated {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.uiState.collect { onUiUpdate(it) } }
            }
        }
    }

    private fun onUiUpdate(uiState: LaunchDetailUiState) {
        binding.progressBar.hide()
        when (uiState) {
            is LaunchDetailUiState.Error -> {}
            is LaunchDetailUiState.Loading -> binding.progressBar.show()
            is LaunchDetailUiState.Success -> {
                updateUi(uiState.launch)
                if (binding.viewPager.adapter == null) {
                    initTabs()
                }
            }
        }
    }

    private fun updateUi(launch: Launch) {
        binding.collapsingToolbar.title = launch.name
        binding.status.setStatus(launch.status, launch.status.name)
        Glide.with(this)
            .load(launch.image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.launchCover)
    }

    private fun createTabs() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(resources.getString(R.string.tab_launch_detail_overview)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.tab_launch_detail_mission))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.tab_launch_detail_vehicles))
    }

    private fun initTabs() {
        val launchId = args.launchId

        adapter = LaunchDetailViewPagerAdapter(launchId, childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.tab_launch_detail_overview)
                1 -> tab.text = resources.getString(R.string.tab_launch_detail_mission)
                2 -> tab.text = resources.getString(R.string.tab_launch_detail_vehicles)
            }
        }.attach()
    }
}
