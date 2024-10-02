/*
 *
 *  * SpaceHub - Designed and Developed by LPirro (Leonardo Pirro)
 *  * Copyright (C) 2023 Leonardo Pirro
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.lpirro.spacehub.news.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lpirro.spacehub.core.exceptions.SearchCancellationException
import com.lpirro.spacehub.news.domain.model.Article
import com.lpirro.spacehub.news.domain.usecase.FilterNewsUseCase
import com.lpirro.spacehub.news.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val filterNewsUseCase: FilterNewsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState(isLoading = true))

    var articles: List<Article> = emptyList()

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    private val _searchLoadingState: MutableState<Boolean> = mutableStateOf(false)
    val searchLoadingState = _searchLoadingState

    private var searchArticlesJob: Job? = null

    val uiState =
        _uiState
            .onStart { getNews() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = NewsUiState(isLoading = true),
            )

    fun getNews(isRefresh: Boolean = false) = viewModelScope.launch {
        if (isRefresh) {
            _uiState.value = _uiState.value.copy(isRefresh = true, isLoading = false)
        }
        getNewsUseCase()
            .catch { _uiState.value = NewsUiState(error = true) }
            .collect {
                articles = it
                _uiState.value = NewsUiState(articles = it)
            }
    }

    fun updateSearchWidgetState(newState: SearchWidgetState) {
        if (newState == SearchWidgetState.CLOSED) {
            _uiState.value = NewsUiState(articles = articles)
        }

        // if the articles are empty we don't want to search
        if (articles.isNotEmpty()) {
            _searchWidgetState.value = newState
        }
    }

    fun updateSearchTextState(searchQuery: String) {
        _searchTextState.value = searchQuery
        if (searchQuery.length > 3) {
            searchArticles(searchQuery)
        }
    }

    private fun searchArticles(searchQuery: String) {
        searchArticlesJob?.cancel(SearchCancellationException())
        _searchLoadingState.value = true

        searchArticlesJob = viewModelScope.launch {
            delay(800)
            filterNewsUseCase(searchQuery)
                .catch {
                    if (it !is SearchCancellationException) {
                        _uiState.value = NewsUiState(searchError = true)
                    }
                }
                .onCompletion { _searchLoadingState.value = false }
                .collectLatest {
                    _uiState.value = NewsUiState(articles = it)
                }
        }
    }
}

data class NewsUiState(
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: Boolean = false,
    val searchError: Boolean = false,
)

enum class SearchWidgetState {
    OPENED,
    CLOSED,
}
