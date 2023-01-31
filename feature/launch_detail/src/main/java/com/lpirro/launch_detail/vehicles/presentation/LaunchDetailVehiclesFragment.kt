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

package com.lpirro.launch_detail.vehicles.presentation

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
import com.lpirro.core.ui.recyclerview.decorator.VerticalSpaceItemDecoration
import com.lpirro.launch_detail.databinding.FragmentLaunchDetailVeichlesBinding
import com.lpirro.launch_detail.vehicles.model.LaunchVehiclesItem
import com.lpirro.launch_detail.vehicles.presentation.delegate.launcherStageDelegate
import com.lpirro.launch_detail.vehicles.presentation.delegate.rocketDelegate
import com.lpirro.launch_detail.vehicles.viewmodel.LaunchDetailVehiclesUiState
import com.lpirro.launch_detail.vehicles.viewmodel.LaunchDetailVehiclesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LaunchDetailVehiclesFragment : BaseFragment<FragmentLaunchDetailVeichlesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLaunchDetailVeichlesBinding
        get() = FragmentLaunchDetailVeichlesBinding::inflate

    private val viewModel: LaunchDetailVehiclesViewModel by viewModels()
    private lateinit var delegateAdapter: ListDelegationAdapter<List<LaunchVehiclesItem>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        registerObservers()

        val launchId = requireArguments().getString(EXTRA_LAUNCH_ID)!!
        viewModel.getLaunch(launchId)
    }

    private fun onUiUpdate(uiState: LaunchDetailVehiclesUiState) {
        when (uiState) {
            is LaunchDetailVehiclesUiState.Error -> {}
            is LaunchDetailVehiclesUiState.Loading -> {}
            is LaunchDetailVehiclesUiState.Success -> {
                delegateAdapter.items = uiState.launchVehicles
                if (binding.launchVehiclesRecyclerView.adapter == null) {
                    binding.launchVehiclesRecyclerView.adapter = delegateAdapter
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val spacing = resources.getDimensionPixelSize(com.lpirro.core.R.dimen.margin_20dp)
        delegateAdapter = ListDelegationAdapter(
            rocketDelegate(),
            launcherStageDelegate()
        )

        binding.launchVehiclesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalSpaceItemDecoration(spaceSize = spacing, edgeSpacing = spacing))
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
        fun newInstance(launchId: String): LaunchDetailVehiclesFragment {
            val bundle = bundleOf(EXTRA_LAUNCH_ID to launchId)
            val fragment = LaunchDetailVehiclesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
