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
package com.spacehub.launcheslist.presentation.mapper

import com.spacehub.common.models.domain.Launch
import com.spacehub.core.util.DateParser
import com.spacehub.launcheslist.presentation.model.LaunchListItemUiModel

interface LaunchListUiMapper {
    fun mapToUi(launch: Launch): LaunchListItemUiModel
}

class LaunchListUiMapperImpl(
    private val dateParser: DateParser,
) : LaunchListUiMapper {
    override fun mapToUi(launch: Launch) = LaunchListItemUiModel(
        id = launch.id,
        title = launch.name,
        agency = launch.launchServiceProvider.name,
        dateTime = dateParser.parseFullDate(launch.net),
        status = launch.status,
        launchImageUrl = launch.image,
    )
}
