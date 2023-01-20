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

package com.lpirro.news.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lpirro.core.base.BaseFragment
import com.lpirro.news.databinding.FragmentNewsBinding
import com.lpirro.news.viewmodel.NewsUiState
import com.lpirro.news.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNewsBinding
        get() = FragmentNewsBinding::inflate

    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObservers()
    }

    private fun registerObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.uiState.collect { onUiUpdate(it) } }
            }
        }
    }

    private fun onUiUpdate(uiState: NewsUiState) {
        // resetViews()
        when (uiState) {
            is NewsUiState.Error -> {}
            is NewsUiState.Loading -> {}
            is NewsUiState.Success -> {
                val c = ""
            }
            is NewsUiState.Refresh -> {}
        }
    }
}
