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

import com.spacehub.common.models.domain.Location
import com.spacehub.common.models.domain.MapPosition
import com.spacehub.common.models.domain.Pad

object MockPad {
    fun create(
        location: Location = MockLocation.create(),
        mapPosition: MapPosition = MockMapPosition.create(),
    ) = Pad(
        id = 39,
        url = "https://example.com/pads/39",
        agencyId = 1,
        name = "Launch Complex 39A",
        infoUrl = "https://example.com/pad-info/39",
        wikiUrl = "https://en.wikipedia.org/wiki/Launch_Complex_39A",
        mapUrl = "https://maps.example.com/pad/39",
        location = location,
        totalLaunchCount = 130,
        orbitalLaunchAttemptCount = 100,
        mapPosition = mapPosition,
    )
}
