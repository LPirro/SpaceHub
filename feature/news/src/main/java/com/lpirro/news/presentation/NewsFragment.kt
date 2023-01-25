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
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lpirro.core.base.BaseFragment
import com.lpirro.core.extensions.hide
import com.lpirro.core.extensions.launchChromeCustomTab
import com.lpirro.core.extensions.onMenuItemActionCollapse
import com.lpirro.core.extensions.onQueryTextChange
import com.lpirro.core.extensions.show
import com.lpirro.core.navigation.NavigationUtil
import com.lpirro.news.R
import com.lpirro.news.databinding.FragmentNewsBinding
import com.lpirro.news.presentation.adapter.ArticleAdapter
import com.lpirro.news.viewmodel.NewsUiState
import com.lpirro.news.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNewsBinding
        get() = FragmentNewsBinding::inflate

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var articleAdapter: ArticleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.inflateMenu(R.menu.news_menu)

        setupRecyclerView()
        registerObservers()
        setupSearchView()

        binding.errorView.retryClickListener = viewModel::refresh
        binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }
    }

    private fun onUiUpdate(uiState: NewsUiState) {
        binding.errorView.hide()
        when (uiState) {
            is NewsUiState.Error -> binding.errorView.show()
            is NewsUiState.Loading -> binding.swipeRefresh.isRefreshing = uiState.isLoading
            is NewsUiState.Success -> {
                articleAdapter.submitList(uiState.articles)
                binding.articlesRecyclerView.layoutManager?.scrollToPosition(0)
            }
        }
    }

    // TODO: Move to View Model
    private fun articleClicked(articleUrl: String) {
        launchChromeCustomTab(articleUrl)
    }

    // TODO: Move to View Model
    private fun relatedLaunchClicked(launchId: String) {
        findNavController().navigate(NavigationUtil.launchDetailDeeplink(launchId))
    }

    private fun setupSearchView() {
        val searchMenuItem = binding.toolbar.menu.findItem(R.id.menu_search)
        val searchView = searchMenuItem.actionView as SearchView

        searchView.onQueryTextChange { newText ->
            if (newText.length > 3) { viewModel.filterArticles(newText) }
        }

        searchMenuItem.onMenuItemActionCollapse { viewModel.getArticles() }
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter(::articleClicked, ::relatedLaunchClicked)
        binding.articlesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = articleAdapter
        }
    }

    private fun registerObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.uiState.collect { onUiUpdate(it) } }
            }
        }
    }
}
