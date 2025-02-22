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

package com.spacehub.launchdetail.presentation.mission.model

import com.spacehub.common.models.domain.Status
import com.spacehub.common.models.domain.Update

data class LaunchDetailMissionUi(
    val missionHeaderSection: MissionHeaderUi,
    val descriptionSection: DescriptionSection,
    val launchInfoSection: LaunchInfoSection,
    val updatesSection: UpdatesSection?
)

data class MissionHeaderUi(
    val name: String,
    val orbit: String,
    val type: String,
    val agencyName: String,
    val missionPatchImageUrl: String?,
)

data class DescriptionSection(
    val description: String,
    val moreInfoUrl: String?
)

data class LaunchInfoSection(
    val status: Status,
    val net: String,
    val windowStart: String,
    val windowEnd: String,
)

data class UpdatesSection(
    val updates: List<Update>
)