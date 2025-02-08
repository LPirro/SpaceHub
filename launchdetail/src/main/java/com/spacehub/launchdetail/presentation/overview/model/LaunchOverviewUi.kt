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

package com.spacehub.launchdetail.presentation.overview.model

data class LaunchOverviewUi(
    val countdownSection: CountdownUi,
    val launchpadSection: LaunchpadUi,
    val watchLiveUrl: String?,
    val agencySection: AgencyUi,
    val trajectoryUrl: String?,
)

data class CountdownUi(
    val launchDate: String,
    val targetDateMillis: Long?,
)

data class LaunchpadUi(
    val name: String,
    val location: String,
    val totalLaunchCount: String,
    val infoUrl: String?,
    val wikiUrl: String?,
    val mapUrl: String?,
    val mapImageHeaderUrl: String?,
)

data class AgencyUi(
    val name: String,
    val countryCode: String,
    val administrator: String,
    val foundingYear: String,
    val totalLaunchCount: String,
    val logoUrl: String?,
)
