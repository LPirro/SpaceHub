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

package com.lpirro.launches.past.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lpirro.core.base.BaseFragment
import com.lpirro.core.extensions.hide
import com.lpirro.core.extensions.show
import com.lpirro.core.navigation.NavigationUtil
import com.lpirro.core.ui.recyclerview.adapter.LaunchesAdapter
import com.lpirro.core.ui.recyclerview.decorator.VerticalSpaceItemDecoration
import com.lpirro.domain.models.Status
import com.lpirro.launches.databinding.FragmentPastLaunchesBinding
import com.lpirro.launches.past.viewmodel.PastLaunchesUiState
import com.lpirro.launches.past.viewmodel.PastLaunchesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PastLaunchesFragment : BaseFragment<FragmentPastLaunchesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPastLaunchesBinding
        get() = FragmentPastLaunchesBinding::inflate

    private val viewModel: PastLaunchesViewModel by viewModels()
    private lateinit var launchesAdapter: LaunchesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObservers()
        setupRecyclerView()
        binding.errorView.retryClickListener = viewModel::refresh
        binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }
    }

    override fun onPause() {
        super.onPause()
        binding.swipeRefresh.isRefreshing = false
    }

    private fun registerObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.uiState.collect { onUiUpdate(it) } }
            }
        }
    }

    private fun onUiUpdate(uiState: PastLaunchesUiState) {
        binding.errorView.hide()
        when (uiState) {
            is PastLaunchesUiState.Error -> binding.errorView.show()
            is PastLaunchesUiState.Loading -> binding.swipeRefresh.isRefreshing = uiState.isLoading
            is PastLaunchesUiState.Success -> launchesAdapter.submitList(uiState.launches)
        }
    }

    private fun showStatusDialog(status: Status) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle(status.name)
        builder.setMessage(status.description)
        builder.setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    private fun goToLaunchDetail(launchId: String) {
        findNavController().navigate(NavigationUtil.launchDetailDeeplink(launchId))
    }

    private fun setupRecyclerView() {
        val spacing = resources.getDimensionPixelSize(com.lpirro.core.R.dimen.margin_16dp)
        launchesAdapter = LaunchesAdapter(::goToLaunchDetail, ::showStatusDialog)
        binding.launchesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalSpaceItemDecoration(spaceSize = spacing, edgeSpacing = spacing))
            adapter = launchesAdapter
        }
    }
}
