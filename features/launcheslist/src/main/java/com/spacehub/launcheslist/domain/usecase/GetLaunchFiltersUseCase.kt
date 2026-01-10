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
package com.spacehub.launcheslist.domain.usecase

import com.spacehub.launcheslist.domain.model.LaunchFilter

data class LaunchFilters(
    val agencies: List<LaunchFilter.Agency>,
    val locations: List<LaunchFilter.Location>,
)

interface GetLaunchFiltersUseCase {
    operator fun invoke(): LaunchFilters
}

class GetLaunchFiltersUseCaseImpl : GetLaunchFiltersUseCase {

    override fun invoke(): LaunchFilters = LaunchFilters(
        agencies = listOf(
            LaunchFilter.Agency(id = "115", name = "Arianespace"),
            LaunchFilter.Agency(id = "159", name = "Avio S.p.A"),
            LaunchFilter.Agency(id = "141", name = "Blue Origin"),
            LaunchFilter.Agency(id = "88", name = "China Aerospace Science and Technology Corporation"),
            LaunchFilter.Agency(id = "17", name = "China National Space Administration"),
            LaunchFilter.Agency(id = "265", name = "Firefly Aerospace"),
            LaunchFilter.Agency(id = "31", name = "Indian Space Research Organization"),
            LaunchFilter.Agency(id = "37", name = "Japan Aerospace Exploration Agency"),
            LaunchFilter.Agency(id = "96", name = "Khrunichev State Research and Production Space Center"),
            LaunchFilter.Agency(id = "44", name = "National Aeronautics and Space Administration"),
            LaunchFilter.Agency(id = "257", name = "Northrop Grumman Space Systems"),
            LaunchFilter.Agency(id = "147", name = "Rocket Lab"),
            LaunchFilter.Agency(id = "63", name = "Russian Federal Space Agency (ROSCOSMOS)"),
            LaunchFilter.Agency(id = "121", name = "SpaceX"),
            LaunchFilter.Agency(id = "124", name = "United Launch Alliance"),
        ),
        locations = listOf(
            LaunchFilter.Location(id = "12", name = "Cape Canaveral"),
            LaunchFilter.Location(id = "27", name = "Kennedy Space Center"),
        ),
    )
}
