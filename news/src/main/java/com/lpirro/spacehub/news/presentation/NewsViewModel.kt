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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lpirro.spacehub.news.domain.model.Article
import com.lpirro.spacehub.news.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState(isLoading = true))

    val uiState =
        _uiState
            .onStart { getNews() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = NewsUiState(isLoading = true),
            )

    fun getNews(isRefresh: Boolean = false) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefresh = isRefresh)
        getNewsUseCase()
            .catch { _uiState.value = _uiState.value.copy(error = true) }
            .onCompletion { _uiState.value = _uiState.value.copy(isRefresh = false) }
            .collect { _uiState.value = NewsUiState(articles = it) }
    }
}

data class NewsUiState(
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: Boolean = false,
)
