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

import com.spacehub.common.models.domain.RocketConfiguration

object MockRocketConfiguration {
    fun create() = RocketConfiguration(
        id = 9,
        name = "Falcon 9",
        manufacturer = MockAgency.create(),
        variant = "variant",
        height = 50.0,
        diameter = 30.0,
        gtoCapacity = null,
        leoCapacity = null,
        toThrust = null,
        apogee = null,
        reusable = true,
        successfulLaunches = 100,
        consecutiveSuccessfulLaunches = 10,
        failedLaunches = 5,
        pendingLaunches = 1,
        launchCost = "1000000",
        infoUrl = null,
        wikiUrl = null,
        minStage = null,
        maxStage = null,
        description = "description",
        imageUrl = null,
    )
}
