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

package com.lpirro.launch_detail.overview.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lpirro.core.base.BaseFragment
import com.lpirro.core.extensions.launchAddToCalendarIntent
import com.lpirro.core.extensions.launchChromeCustomTab
import com.lpirro.core.extensions.launchGoogleMapsIntent
import com.lpirro.launch_detail.R
import com.lpirro.launch_detail.databinding.FragmentLaunchDetailOverviewBinding
import com.lpirro.launch_detail.overview.model.LaunchOverviewItem
import com.lpirro.launch_detail.overview.presentation.delegates.agencyDelegate
import com.lpirro.launch_detail.overview.presentation.delegates.countDownHeaderDelegate
import com.lpirro.launch_detail.overview.presentation.delegates.launchpadDelegate
import com.lpirro.launch_detail.overview.presentation.delegates.locationDelegate
import com.lpirro.launch_detail.overview.presentation.delegates.trajectoryDelegate
import com.lpirro.launch_detail.overview.presentation.delegates.watchLiveDelegate
import com.lpirro.launch_detail.overview.viewmodel.LaunchDetailOverviewEvent
import com.lpirro.launch_detail.overview.viewmodel.LaunchDetailOverviewUiState
import com.lpirro.launch_detail.overview.viewmodel.LaunchDetailOverviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LaunchDetailOverviewFragment : BaseFragment<FragmentLaunchDetailOverviewBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLaunchDetailOverviewBinding
        get() = FragmentLaunchDetailOverviewBinding::inflate

    private val viewModel: LaunchDetailOverviewViewModel by viewModels()
    private lateinit var delegateAdapter: ListDelegationAdapter<List<LaunchOverviewItem>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        registerObservers()

        val launchId = requireArguments().getString(EXTRA_LAUNCH_ID)!!
        viewModel.getLaunch(launchId)
    }

    private fun onUiUpdate(uiState: LaunchDetailOverviewUiState) {
        when (uiState) {
            is LaunchDetailOverviewUiState.Error -> {}
            is LaunchDetailOverviewUiState.Loading -> {}
            is LaunchDetailOverviewUiState.Success -> {
                delegateAdapter.items = uiState.launchOverview
                if (binding.launchOverviewRecyclerView.adapter == null) {
                    binding.launchOverviewRecyclerView.adapter = delegateAdapter
                }
            }
        }
    }

    private fun onEvent(event: LaunchDetailOverviewEvent) {
        when (event) {
            is LaunchDetailOverviewEvent.AddToCalendar -> {
                launchAddToCalendarIntent(event.launchName, event.launchDateMillis)
            }
            is LaunchDetailOverviewEvent.OpenLaunchTrajectory -> launchChromeCustomTab(event.url)
            is LaunchDetailOverviewEvent.OpenGoogleMaps -> launchGoogleMapsIntent(event.url)
            is LaunchDetailOverviewEvent.OpenChromeCustomTab -> launchChromeCustomTab(event.url)
            is LaunchDetailOverviewEvent.OpenYouTubeInFullScreen -> openYouTubeInFullScreen(event.videoId)
        }
    }

    private fun openYouTubeInFullScreen(videoId: String) {
        findNavController().navigate(
            R.id.navigation_youtube_full_screen,
            bundleOf("youtubeId" to videoId)
        )
    }

    private fun setupRecyclerView() {
        delegateAdapter = ListDelegationAdapter(
            countDownHeaderDelegate(viewModel::addLaunchToCalendar),
            watchLiveDelegate(viewModel::openYouTubeInFullScreen),
            agencyDelegate(),
            locationDelegate(),
            trajectoryDelegate(viewModel::openLaunchTrajectory),
            launchpadDelegate(
                viewModel::openGoogleMaps,
                viewModel::openChromeCustomTab,
                viewModel::openChromeCustomTab
            ),
        )

        binding.launchOverviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(LaunchOverviewItemDecorator())
        }
    }

    private fun registerObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.uiState.collect { onUiUpdate(it) } }
                launch { viewModel.events.collect { onEvent(it) } }
            }
        }
    }

    companion object {
        private const val EXTRA_LAUNCH_ID = "extra_launch_id"
        fun newInstance(launchId: String): LaunchDetailOverviewFragment {
            val bundle = bundleOf(EXTRA_LAUNCH_ID to launchId)
            val fragment = LaunchDetailOverviewFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
