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

package com.spacehub.launches.mocks

import com.spacehub.launches.presentation.model.LaunchUiModel
import com.spacehub.testutil.mocks.MockStatus

object MockLaunchUi {
    fun create() = LaunchUiModel(
        id = "launch-001",
        title = "Falcon 9 - Starlink 30",
        agency = "SpaceX",
        location = "Cape Canaveral",
        dateTime = "2023-01-01T00:00:00Z",
        netMillis = 1672531200000,
        status = MockStatus.create(),
        launchImageUrl = "https://example.com/images/starlink30.jpg",
    )
}
