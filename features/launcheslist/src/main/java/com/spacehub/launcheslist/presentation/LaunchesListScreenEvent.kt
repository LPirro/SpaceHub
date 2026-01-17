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

sealed interface LaunchesListScreenEvent {
    data object AgencyFilterClick : LaunchesListScreenEvent
    data object LocationFilterClick : LaunchesListScreenEvent
    data object BottomSheetDismiss : LaunchesListScreenEvent
    data class AgencyFiltersConfirmed(val agencies: Set<LaunchFilter.Agency>) : LaunchesListScreenEvent
    data class LocationFiltersConfirmed(val locations: Set<LaunchFilter.Location>) : LaunchesListScreenEvent
    data class LaunchClick(val id: String, val name: String) : LaunchesListScreenEvent
    data object BackClick : LaunchesListScreenEvent
}
