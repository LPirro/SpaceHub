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

import com.spacehub.launcheslist.domain.model.LaunchFilter

data class LaunchesListUiState(
    val agencyFilters: List<LaunchFilter.Agency> = emptyList(),
    val locationFilters: List<LaunchFilter.Location> = emptyList(),
    val selectedAgencies: Set<LaunchFilter.Agency> = emptySet(),
    val selectedLocations: Set<LaunchFilter.Location> = emptySet(),
    val activeBottomSheet: ActiveBottomSheet = ActiveBottomSheet.None,
)

sealed interface ActiveBottomSheet {
    data object None : ActiveBottomSheet
    data object Agency : ActiveBottomSheet
    data object Location : ActiveBottomSheet
}