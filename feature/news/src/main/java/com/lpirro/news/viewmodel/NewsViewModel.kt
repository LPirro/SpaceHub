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

package com.lpirro.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lpirro.core.exceptions.SearchCancellationException
import com.lpirro.domain.usecase.FilterArticlesUseCase
import com.lpirro.domain.usecase.GetArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val filterArticlesUseCase: FilterArticlesUseCase
) : ViewModel(), NewsViewModelContract {

    private val _uiState =
        MutableStateFlow<NewsUiState>(NewsUiState.Loading(isLoading = true))
    val uiState: StateFlow<NewsUiState> = _uiState

    private var filterArticleJob: Job? = null

    init {
        getArticles()
    }

    override fun getArticles(): Job {
        return getArticlesUseCase()
            .onEach { articles ->
                _uiState.value = NewsUiState.Loading(isLoading = false)
                _uiState.value = NewsUiState.Success(articles)
            }
            .catch {
                _uiState.value = NewsUiState.Loading(isLoading = false)
                _uiState.value = NewsUiState.Error
            }
            .launchIn(viewModelScope)
    }

    override fun filterArticles(queryFilter: String): Job? {
        filterArticleJob?.cancel(SearchCancellationException())
        filterArticleJob = viewModelScope.launch {
            try {
                delay(500)
                _uiState.value = NewsUiState.Loading(isLoading = true)
                filterArticlesUseCase(queryFilter).collect { articles ->
                    _uiState.value = NewsUiState.Loading(isLoading = false)
                    _uiState.value = NewsUiState.Success(articles)
                }
            } catch (e: Exception) {
                _uiState.value = NewsUiState.Loading(isLoading = false)
                if (e !is SearchCancellationException) _uiState.value = NewsUiState.Error
            }
        }
        return filterArticleJob
    }

    override fun refresh() {
        NewsUiState.Loading(isLoading = true)
        getArticles()
    }
}
