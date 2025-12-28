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
package com.spacehub.launches.presentation

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.spacehub.common.models.domain.Status
import com.spacehub.launches.presentation.model.LaunchUi

class LaunchesScreenPreviewProvider : PreviewParameterProvider<LaunchesUiState> {
    override val values: Sequence<LaunchesUiState>
        get() = sequenceOf(
            LaunchesUiState.Loading(true),
            LaunchesUiState.Success(
                upcomingLaunches = listOf(
                    fakeUpcomingLaunch.copy(id = "1", title = "Falcon 9 - Starlink 30"),
                    fakeUpcomingLaunch.copy(id = "2", title = "Atlas V - USSF 51"),
                    fakeUpcomingLaunch.copy(id = "3", title = "Ariane 6 - Maiden Flight"),
                ),
                pastLaunches = listOf(
                    fakePastLaunch.copy(id = "4", title = "Starship Flight 4"),
                    fakePastLaunch.copy(id = "5", title = "Crew Dragon - Crew 8"),
                    fakePastLaunch.copy(id = "6", title = "New Glenn - NG-1"),
                )
            ),
            LaunchesUiState.Error
        )

    private val fakeUpcomingLaunch = LaunchUi(
        id = "launch-001",
        title = "Falcon 9 - Starlink 30",
        agency = "SpaceX",
        location = "Kennedy Space Center",
        dateTime = "1 Nov 2025 • 4:30 pm",
        netMillis = System.currentTimeMillis() + 86400000L, // Tomorrow
        status = Status.Go(
            name = "GO",
            abbrev = "GO",
            description = "Launch Scheduled",
        ),
        launchImageUrl = "",
    )

    private val fakePastLaunch = LaunchUi(
        id = "launch-002",
        title = "Starship Flight 4",
        agency = "SpaceX",
        location = "Starbase, TX",
        dateTime = "6 Jun 2024 • 7:50 am",
        netMillis = System.currentTimeMillis() - 86400000L, // Yesterday
        status = Status.Success(
            name = "Success",
            abbrev = "Success",
            description = "Launch Successful",
        ),
        launchImageUrl = "",
    )
}