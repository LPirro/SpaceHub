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

package com.lpirro.launch_detail.mission.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.lpirro.core.base.BaseFragment
import com.lpirro.core.extensions.launchChromeCustomTab
import com.lpirro.launch_detail.databinding.FragmentLaunchDetailMissionBinding
import com.lpirro.launch_detail.mission.model.LaunchMissionItem
import com.lpirro.launch_detail.mission.presentation.delegate.missionDescriptionDelegate
import com.lpirro.launch_detail.mission.presentation.delegate.missionDetailsUnavailableDelegate
import com.lpirro.launch_detail.mission.presentation.delegate.missionHeaderDelegate
import com.lpirro.launch_detail.mission.presentation.delegate.missionLaunchInfoDelegate
import com.lpirro.launch_detail.mission.presentation.delegate.missionUpdatesDelegate
import com.lpirro.launch_detail.mission.viewmodel.LaunchDetailMissionUiState
import com.lpirro.launch_detail.mission.viewmodel.LaunchDetailMissionViewModel
import com.lpirro.launch_detail.overview.presentation.LaunchOverviewItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LaunchDetailMissionFragment : BaseFragment<FragmentLaunchDetailMissionBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLaunchDetailMissionBinding
        get() = FragmentLaunchDetailMissionBinding::inflate

    private val viewModel: LaunchDetailMissionViewModel by viewModels()
    private lateinit var delegateAdapter: ListDelegationAdapter<List<LaunchMissionItem>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        registerObservers()

        val launchId = requireArguments().getString(EXTRA_LAUNCH_ID)!!
        viewModel.getLaunch(launchId)
    }

    private fun onUiUpdate(uiState: LaunchDetailMissionUiState) {
        when (uiState) {
            is LaunchDetailMissionUiState.Error -> {}
            is LaunchDetailMissionUiState.Loading -> {}
            is LaunchDetailMissionUiState.Success -> {
                delegateAdapter.items = uiState.launchMission
                if (binding.launchMissionRecyclerView.adapter == null) {
                    binding.launchMissionRecyclerView.adapter = delegateAdapter
                }
            }
        }
    }

    private fun setupRecyclerView() {
        delegateAdapter = ListDelegationAdapter(
            missionHeaderDelegate(),
            missionDescriptionDelegate { launchChromeCustomTab(it) },
            missionLaunchInfoDelegate(),
            missionDetailsUnavailableDelegate(),
            missionUpdatesDelegate()
        )

        binding.launchMissionRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(LaunchOverviewItemDecorator())
        }
    }

    private fun registerObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.uiState.collect { onUiUpdate(it) } }
            }
        }
    }

    companion object {
        private const val EXTRA_LAUNCH_ID = "extra_launch_id"
        fun newInstance(launchId: String): LaunchDetailMissionFragment {
            val bundle = bundleOf(EXTRA_LAUNCH_ID to launchId)
            val fragment = LaunchDetailMissionFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
