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
package com.lpirro.spacehub.launches.presentation

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.lpirro.spacehub.launches.presentation.model.LaunchUi
import com.spacehub.common.models.domain.Status

class SampleLaunchesProvider : PreviewParameterProvider<List<LaunchUi>> {
    override val values: Sequence<List<LaunchUi>>
        get() = sequenceOf(listOf(fakeLaunch, fakeLaunch, fakeLaunch))

    private val fakeLaunch = LaunchUi(
        id = "launch-001",
        title = "Falcon 9 - Starlink 30",
        agency = "SpaceX",
        location = "Kennedy Space Center",
        dateTime = "2024-09-25T14:30:00Z",
        netMillis = System.currentTimeMillis(),
        status = Status.Go(
            name = "GO",
            abbrev = "GO",
            description = "Launch Scheduled",
        ),
        launchImageUrl = "",
    )
}
