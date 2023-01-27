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

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.lpirro.domain.models.Launch
import com.lpirro.launch_detail.R
import com.lpirro.launch_detail.databinding.ActivityLaunchDetailBinding
import com.lpirro.launch_detail.tabs.viewmodel.LaunchDetailUiState
import com.lpirro.launch_detail.tabs.viewmodel.LaunchDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LaunchDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchDetailBinding
    private val args: LaunchDetailActivityArgs by navArgs()
    private val viewModel: LaunchDetailViewModel by viewModels()

    private lateinit var adapter: LaunchDetailViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityLaunchDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        var maxDeltaPadding = 0
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            maxDeltaPadding = insets.top
            binding.root.updatePadding(bottom = insets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        val appBarTotalScrollRange: Float by lazy {
            binding.appBar.totalScrollRange.toFloat()
        }

        binding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val percentOfScrollRange = (-verticalOffset / appBarTotalScrollRange)
            val deltaPadding = maxDeltaPadding * percentOfScrollRange
            val newTopPadding = deltaPadding.toInt()
            if (newTopPadding != appBarLayout.paddingTop) {
                appBarLayout.updatePadding(top = newTopPadding)
            }
        }

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

        adapter = LaunchDetailViewPagerAdapter(launchId, this)
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
