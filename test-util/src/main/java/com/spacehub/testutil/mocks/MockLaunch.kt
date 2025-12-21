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
package com.spacehub.testutil.mocks

import com.spacehub.common.models.domain.Launch

object MockLaunch {
    fun create() = Launch(
        id = "launch-001",
        name = "Falcon 9 - Starlink 30",
        image = "https://example.com/images/starlink30.jpg",
        net = "2024-09-25T15:00:00Z",
        launchServiceProvider = MockAgency.create(),
        missionPatches =
        listOf(
            MockMissionPatch.create(),
        ),
        mission = MockMission.create(),
        pad = MockPad.create(),
        windowStart = "2024-09-25T14:00:00Z",
        windowEnd = "2024-09-25T15:00:00Z",
        netMillis = 1695741000000L,
        status = MockStatus.create(),
        watchLiveUrls = listOf(
            MockUrl.create(),
        ),
        infoUrl = "https://spacex.com/starlink30",
        flightClubUrl = "https://flightclub.io/starlink30",
        updates = listOf(
            MockUpdate.create(),
        ),
        rocket = MockRocket.create(),
    )
}
