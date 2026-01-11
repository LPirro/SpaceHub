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
package com.spacehub.launcheslist.presentation

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.spacehub.common.models.domain.Status
import com.spacehub.launcheslist.presentation.model.LaunchListItemUiModel
import kotlinx.coroutines.flow.MutableStateFlow

class LaunchListPreviewProvider : PreviewParameterProvider<MutableStateFlow<PagingData<LaunchListItemUiModel>>> {

    private val fakeItems = List(10) { index ->
        LaunchListItemUiModel(
            id = "id_$index",
            title = "Falcon 9 Block 5 | Starlink Group ${index + 1}",
            agency = "SpaceX",
            dateTime = "Dec 15, 2025 • 10:30 AM",
            status = Status.Go(
                name = "Go for Launch",
                abbrev = "Go",
                description = "Current status is go for launch.",
            ),
            launchImageUrl = null,
        )
    }

    override val values: Sequence<MutableStateFlow<PagingData<LaunchListItemUiModel>>> = sequenceOf(
        MutableStateFlow(
            PagingData.from(
                data = fakeItems,
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(endOfPaginationReached = false),
                    prepend = LoadState.NotLoading(endOfPaginationReached = true),
                    append = LoadState.NotLoading(endOfPaginationReached = false),
                ),
            )
        )
    )
}