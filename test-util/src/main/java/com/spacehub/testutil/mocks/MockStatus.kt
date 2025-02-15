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

import com.spacehub.common.models.domain.Status

object MockStatus {
    fun create(
        status: Status = Status.Go(
            name = "GO",
            abbrev = "GO",
            description = "Launch Scheduled",
        ),
    ): Status = when (status) {
        is Status.Failure -> Status.Failure(
            name = "FAILURE",
            abbrev = "FAILURE",
            description = "Launch Failed",
        )

        is Status.Go -> Status.Go(
            name = "GO",
            abbrev = "GO",
            description = "Launch Scheduled",
        )

        is Status.InFlight -> Status.InFlight(
            name = "IN_FLIGHT",
            abbrev = "IN_FLIGHT",
            description = "Launch In Flight",
        )

        is Status.Success -> Status.Success(
            name = "SUCCESS",
            abbrev = "SUCCESS",
            description = "Launch Successful",
        )

        is Status.TBC -> Status.TBC(
            name = "TBC",
            abbrev = "TBC",
            description = "To Be Confirmed",
        )

        is Status.TBD -> Status.TBD(
            name = "TBD",
            abbrev = "TBD",
            description = "To Be Determined",
        )

        is Status.Unknown -> Status.Unknown(
            name = "UNKNOWN",
            abbrev = "UNKNOWN",
            description = "Unknown Status",
        )
    }
}
