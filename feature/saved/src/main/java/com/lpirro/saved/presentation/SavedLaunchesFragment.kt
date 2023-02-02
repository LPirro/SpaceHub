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

package com.lpirro.saved.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
import com.lpirro.core.ui.NavDrawerInteraction
import com.lpirro.core.ui.recyclerview.adapter.LaunchesAdapter
import com.lpirro.core.ui.recyclerview.decorator.VerticalSpaceItemDecoration
import com.lpirro.domain.models.Status
import com.lpirro.saved.databinding.FragmentSavedLaunchesBinding
import com.lpirro.saved.viewmodel.SavedLaunchesUiState
import com.lpirro.saved.viewmodel.SavedLaunchesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedLaunchesFragment : BaseFragment<FragmentSavedLaunchesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSavedLaunchesBinding
        get() = FragmentSavedLaunchesBinding::inflate

    private val viewModel: SavedLaunchesViewModel by viewModels()
    private lateinit var launchesAdapter: LaunchesAdapter

    private lateinit var navDrawerInteraction: NavDrawerInteraction

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navDrawerInteraction = context as NavDrawerInteraction
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navigationIcon =
            ContextCompat.getDrawable(requireContext(), com.lpirro.core.R.drawable.menu)
        binding.toolbar.navigationIcon = navigationIcon
        binding.toolbar.setNavigationOnClickListener {
            navDrawerInteraction.openDrawer()
        }

        registerObservers()
        setupRecyclerView()
        binding.emptyView.exploreButton.setOnClickListener {
            findNavController().navigate(NavigationUtil.launchesDeeplink())
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getSavedLaunches()
    }

    private fun onUiUpdate(uiState: SavedLaunchesUiState) {
        binding.errorView.hide()
        binding.emptyView.root.hide()
        when (uiState) {
            is SavedLaunchesUiState.Error -> binding.errorView.show()
            is SavedLaunchesUiState.Loading -> {}
            is SavedLaunchesUiState.Success -> launchesAdapter.submitList(uiState.launches)
            is SavedLaunchesUiState.NoSavedLaunches -> binding.emptyView.root.show()
        }
    }

    private fun setupRecyclerView() {
        val spacing = resources.getDimensionPixelSize(com.lpirro.core.R.dimen.margin_16dp)
        launchesAdapter = LaunchesAdapter(::goToLaunchDetail, ::showStatusDialog)
        binding.savedLaunchesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                VerticalSpaceItemDecoration(
                    spaceSize = spacing,
                    edgeSpacing = spacing
                )
            )
            adapter = launchesAdapter
        }
    }

    private fun registerObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.uiState.collect { onUiUpdate(it) } }
            }
        }
    }

    private fun goToLaunchDetail(launchId: String) {
        findNavController().navigate(NavigationUtil.launchDetailDeeplink(launchId))
    }

    private fun showStatusDialog(status: Status) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle(status.name)
        builder.setMessage(status.description)
        builder.setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
        builder.show()
    }
}
