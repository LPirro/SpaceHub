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

package com.spacehub.launchdetail.presentation.mission

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.spacehub.core.ui.composables.InfoCard
import com.spacehub.core.ui.theme.SpacehubTheme
import com.spacehub.common.models.domain.Status
import com.spacehub.launchdetail.R
import com.spacehub.launchdetail.presentation.mission.model.DescriptionSection
import com.spacehub.launchdetail.presentation.mission.model.LaunchDetailMissionUi
import com.spacehub.launchdetail.presentation.mission.model.LaunchInfoSection
import com.spacehub.launchdetail.presentation.mission.model.MissionHeaderUi

@Composable
internal fun LaunchDetailMission(
    uiState: LaunchDetailMissionViewModel.LaunchDetailMissionUiState,
    onMoreInfoClicked: (url: String) -> Unit,
) {
    when {
        uiState.isLoading -> {
            CircularProgressIndicator(
                Modifier
                    .fillMaxSize()
                    .wrapContentSize(),
            )
        }

        uiState.error -> {
        }

        else -> {
            LaunchDetailMissionContent(
                modifier = Modifier.fillMaxSize(),
                onMoreInfoClicked = onMoreInfoClicked,
                launchMissionUi = uiState.launchMissionUi!!,
            )
        }
    }
}

@Composable
private fun LaunchDetailMissionContent(
    modifier: Modifier = Modifier,
    launchMissionUi: LaunchDetailMissionUi,
    onMoreInfoClicked: (url: String) -> Unit,
) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxSize(),
    ) {
        MissionHeaderSection(
            missionHeaderUi = launchMissionUi.missionHeaderSection,
        )

        DescriptionSection(
            modifier = Modifier.padding(16.dp),
            onMoreInfoClicked = onMoreInfoClicked,
            descriptionSection = launchMissionUi.descriptionSection,
        )
    }
}

@Composable
private fun MissionHeaderSection(
    missionHeaderUi: MissionHeaderUi,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model =
                ImageRequest.Builder(LocalContext.current)
                    .data(missionHeaderUi.missionPatchImageUrl)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .size(90.dp),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(
                    text = missionHeaderUi.name,
                    style = MaterialTheme.typography.headlineSmall,
                )

                Spacer(modifier = Modifier.height(4.dp))

                MissionInfoRow(
                    iconId = R.drawable.ic_orbit,
                    stringResource(
                        R.string.launch_detail_mission_header_orbit,
                        missionHeaderUi.orbit,
                    ),
                )
                MissionInfoRow(
                    iconId = R.drawable.ic_rocket_land,
                    stringResource(
                        R.string.launch_detail_mission_header_type,
                        missionHeaderUi.type,
                    ),
                )
                MissionInfoRow(
                    iconId = R.drawable.ic_domain,
                    stringResource(
                        R.string.launch_detail_mission_header_agency,
                        missionHeaderUi.agencyName,
                    ),
                )
            }
        }
    }
}

@Composable
fun MissionInfoRow(
    @DrawableRes iconId: Int,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp),
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun DescriptionSection(
    modifier: Modifier = Modifier,
    descriptionSection: DescriptionSection,
    onMoreInfoClicked: (url: String) -> Unit,
) {
    InfoCard(
        modifier = modifier,
        title = stringResource(R.string.description),
    ) {
        Column {
            Text(
                text = descriptionSection.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(16.dp))

            descriptionSection.moreInfoUrl?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = { onMoreInfoClicked.invoke(it) },
                    ) {
                        Text(text = stringResource(R.string.more_info))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LaunchDetailMissionPreview() {
    SpacehubTheme {
        LaunchDetailMission(
            uiState = LaunchDetailMissionViewModel.LaunchDetailMissionUiState(
                launchMissionUi = LaunchDetailMissionUi(
                    missionHeaderSection = MissionHeaderUi(
                        name = "Starlink 4-11",
                        orbit = "Polar Orbit",
                        agencyName = "SpaceX",
                        type = "Communication",
                        missionPatchImageUrl = "https://images2.img",
                    ),
                    descriptionSection = DescriptionSection(
                        description = "Starlink is a satellite internet constellation project developed by SpaceX providing satellite Internet access. The constellation will consist of thousands of mass-produced small satellites in low Earth orbit (LEO), working in combination with ground transceivers. SpaceX intends to provide satellite Internet connectivity to underserved areas of the planet, as well as provide competitively priced service to urban areas.",
                        moreInfoUrl = "https://en.wikipedia.org/wiki/Starlink",
                    ),
                    launchInfoSection = LaunchInfoSection(
                        status = Status.Go(
                            "Go",
                            "The launch is a go",
                            description = "The launch is a go",
                        ),
                        net = "2023-03-14 10:00:00 UTC",
                        windowStart = "2023-03-14 10:00:00 UTC",
                        windowEnd = "2023-03-14 10:00:00 UTC",
                    ),
                    updatesSection = null,
                ),
            ),
            onMoreInfoClicked = {},
        )
    }
}
