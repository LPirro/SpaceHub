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

package com.spacehub.launchdetail.presentation.overview

import InfoItems
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.outlinedButtonBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lpirro.spacehub.core.ui.composables.CountdownTimer
import com.lpirro.spacehub.core.ui.composables.InfoCard
import com.lpirro.spacehub.core.ui.composables.InfoCardButton
import com.lpirro.spacehub.core.ui.theme.SpacehubTheme
import com.spacehub.launchdetail.R
import com.spacehub.launchdetail.presentation.overview.LaunchDetailOverviewViewModel.*
import com.spacehub.launchdetail.presentation.overview.model.AgencyUi
import com.spacehub.launchdetail.presentation.overview.model.LaunchOverviewUi
import com.spacehub.launchdetail.presentation.overview.model.LaunchpadUi

@Composable
internal fun LaunchDetailOverview(uiState: LaunchDetailOverviewUiState) {

    when {
        uiState.isLoading -> {
            CircularProgressIndicator(
                Modifier
                    .fillMaxSize()
                    .wrapContentSize(),
            )
        }

        uiState.error -> {
            // Error State
        }

        else -> {
            LaunchDetailOverviewContent(uiState = uiState.launchOverviewUi!!)
        }
    }
}

@Composable
private fun LaunchDetailOverviewContent(modifier: Modifier = Modifier, uiState: LaunchOverviewUi) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxSize(),
    ) {
        uiState.countdownSection.targetDateMillis?.let {
            CountdownSection(
                launchDate = uiState.countdownSection.launchDate,
                targetDateMillis = it,
            )
        }

        Column(Modifier.padding(16.dp)) {
            val spacing = 16.dp

            LaunchPadSection(launchpadSection = uiState.launchpadSection)

            Spacer(modifier = Modifier.height(spacing))

            WatchLiveSection()

            Spacer(modifier = Modifier.height(spacing))

            AgencySection(uiState.agencySection)

            Spacer(modifier = Modifier.height(spacing))

            InfoCardButton(text = stringResource(R.string.launch_detail_trajectory))
        }
    }
}

@Composable
private fun AgencySection(agencyUi: AgencyUi) {
    InfoCard(
        title = stringResource(R.string.launch_detail_agency),
        contentImageUrl = agencyUi.logoUrl,
    ) {
        InfoItems(
            listOf(
                stringResource(R.string.launch_detail_name) to agencyUi.name,
                stringResource(R.string.launch_detail_country) to agencyUi.countryCode,
                stringResource(R.string.launch_detail_administrator) to agencyUi.administrator,
                stringResource(R.string.launch_detail_founded) to agencyUi.foundingYear,
                stringResource(R.string.launch_detail_total_launches) to agencyUi.totalLaunchCount,
            ),
        )
    }
}

@Composable
private fun CountdownSection(
    launchDate: String,
    targetDateMillis: Long,
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
        Text(
            text = launchDate,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(8.dp))

        CountdownTimer(targetDateMillis)

        Spacer(modifier = Modifier.height(12.dp))

        CountdownActionButtons()
    }
}

@Composable
fun LaunchPadSection(launchpadSection: LaunchpadUi) {
    InfoCard(
        title = stringResource(R.string.launch_detail_launchpad),
        headerImageUrl = launchpadSection.mapImageHeaderUrl,
    ) {
        InfoItems(
            listOf(
                stringResource(R.string.launch_detail_name) to launchpadSection.name,
                stringResource(R.string.launch_detail_location) to launchpadSection.location,
                stringResource(R.string.launch_detail_total_launches) to launchpadSection.totalLaunchCount,
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
        ) {
            launchpadSection.mapUrl?.let {
                AssistChip(
                    label = { Text(stringResource(R.string.launch_detail_maps)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(AssistChipDefaults.IconSize),
                        )
                    },
                    onClick = { /* Open Map Action */ },
                )
            }
            launchpadSection.infoUrl?.let {
                AssistChip(
                    label = { Text(stringResource(R.string.launch_detail_info)) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_info_variant_circle_outline),
                            contentDescription = null,
                            modifier = Modifier.size(AssistChipDefaults.IconSize),
                        )
                    },
                    onClick = { /* Open Map Action */ },
                )
            }
            launchpadSection.wikiUrl?.let {
                AssistChip(
                    label = { Text(stringResource(R.string.launch_detail_wikipedia)) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_wikipedia),
                            contentDescription = null,
                            modifier = Modifier.size(AssistChipDefaults.IconSize),
                        )
                    },
                    onClick = { /* Open Map Action */ },
                )
            }
        }
    }
}

@Composable
fun WatchLiveSection() {
    InfoCard(title = stringResource(R.string.launch_detail_watch_live), padding = 0.dp) {
        Text(
            text = "TO-DO: YouTube Component",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}

@Composable
private fun CountdownActionButtons() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        OutlinedButton(
            onClick = { /* Add to Calendar Action */ },
            shape = RoundedCornerShape(8.dp),
            border = outlinedButtonBorder(true),
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(R.string.launch_detail_add_to_calendar_content_description),
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.launch_detail_add_to_calendar),
                color = MaterialTheme.colorScheme.primary,
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = { /* Save Action */ },
            shape = RoundedCornerShape(8.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = stringResource(R.string.launch_detail_save_content_description),
                tint = MaterialTheme.colorScheme.onPrimary,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.launch_detail_save),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Preview()
@Composable
private fun LaunchDetailOverviewPreview() {
    SpacehubTheme {
        LaunchDetailOverview(
            LaunchDetailOverviewUiState(
                launchOverviewUi = MockData.launchOverviewUiMock,
                isLoading = false,
                error = false,
            ),
        )
    }
}
