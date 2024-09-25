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
package com.lpirro.spacehub.core.ui.composables

import android.os.CountDownTimer
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lpirro.spacehub.core.R
import com.lpirro.spacehub.core.model.Status
import com.lpirro.spacehub.core.ui.theme.SpacehubTheme
import java.util.concurrent.TimeUnit

@Composable
fun LaunchCard(
    modifier: Modifier = Modifier,
    title: String,
    agency: String,
    location: String,
    dateTime: String,
    netMillis: Long,
    status: Status,
    launchImageUrl: String?,
    onClick: () -> Unit,
) {
    OutlinedCard(
        modifier =
        modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() },
    ) {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (image, launchTitle, infoAgency, infoLocation, infoDate, launchStatus, launchCountdown) = createRefs()

            val placeholderDrawable =
                AppCompatResources.getDrawable(
                    LocalContext.current,
                    R.drawable.launch_image_placeholder,
                )
            placeholderDrawable?.setTint(MaterialTheme.colorScheme.inverseOnSurface.toArgb())

            AsyncImage(
                model =
                ImageRequest.Builder(LocalContext.current)
                    .data(launchImageUrl)
                    .crossfade(true)
                    .error(placeholderDrawable)
                    .placeholder(placeholderDrawable)
                    .build(),
                modifier =
                Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        height = Dimension.fillToConstraints
                        width = Dimension.value(100.dp)
                    }
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )

            Text(
                maxLines = 2,
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier =
                Modifier
                    .constrainAs(launchTitle) {
                        end.linkTo(parent.end, margin = 12.dp)
                        start.linkTo(image.end, margin = 12.dp)
                        top.linkTo(parent.top, margin = 12.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
            )
            LaunchInfoItem(
                text = agency,
                icon = ImageVector.vectorResource(id = R.drawable.domain),
                modifier =
                Modifier.constrainAs(infoAgency) {
                    start.linkTo(launchTitle.start)
                    end.linkTo(launchTitle.end)
                    top.linkTo(launchTitle.bottom, margin = 12.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            )
            LaunchInfoItem(
                modifier =
                Modifier
                    .constrainAs(infoLocation) {
                        top.linkTo(infoAgency.bottom, margin = 4.dp)
                        start.linkTo(launchTitle.start)
                        end.linkTo(launchTitle.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                text = location,
                icon = Icons.Outlined.LocationOn,
            )
            LaunchInfoItem(
                modifier =
                Modifier.constrainAs(infoDate) {
                    top.linkTo(infoLocation.bottom, margin = 4.dp)
                    start.linkTo(launchTitle.start)
                    end.linkTo(launchTitle.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                text = dateTime,
                icon = ImageVector.vectorResource(id = R.drawable.calendar_blank_outline),
            )

            LaunchStatus(
                modifier =
                Modifier.constrainAs(launchStatus) {
                    start.linkTo(launchTitle.start)
                    top.linkTo(infoDate.bottom, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 12.dp)
                    height = Dimension.wrapContent
                    width = Dimension.wrapContent
                },
                status = status,
            )

            LaunchCountdown(
                modifier =
                Modifier.constrainAs(launchCountdown) {
                    end.linkTo(launchTitle.end)
                    top.linkTo(launchStatus.top)
                    bottom.linkTo(launchStatus.bottom)
                    height = Dimension.wrapContent
                },
                targetMillis = netMillis,
            )
        }
    }
}

@Composable
private fun LaunchInfoItem(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
) {
    val componentsColor = MaterialTheme.colorScheme.onSurfaceVariant

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = icon,
            contentDescription = null,
            tint = componentsColor,
        )
        Spacer(modifier = Modifier.padding(start = 4.dp))
        Text(text = text, style = MaterialTheme.typography.labelLarge, color = componentsColor)
    }
}

@Composable
fun LaunchCountdown(
    modifier: Modifier,
    targetMillis: Long,
) {
    // States to hold time values
    var countdownText by remember { mutableStateOf("") }
    var days by remember { mutableLongStateOf(0L) }
    var hours by remember { mutableLongStateOf(0L) }
    var minutes by remember { mutableLongStateOf(0L) }
    var seconds by remember { mutableLongStateOf(0L) }
    var isToday by remember { mutableStateOf(false) }
    var isInThePast by remember { mutableStateOf(false) }

    LaunchedEffect(targetMillis) {
        // Get the time difference
        val currentMillis = System.currentTimeMillis()
        val remainingMillis = targetMillis - currentMillis

        object : CountDownTimer(remainingMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                // Calculate the time difference
                val timeDifference = targetMillis - System.currentTimeMillis()

                days = TimeUnit.MILLISECONDS.toDays(timeDifference)
                hours = TimeUnit.MILLISECONDS.toHours(timeDifference) % 24
                minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference) % 60
                seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifference) % 60
                isToday = days == 0L
            }

            override fun onFinish() {
                this.cancel()
                isInThePast = true
            }
        }.start()
    }

    when {
        isInThePast -> {
            countdownText = ""
        }

        isToday -> {
            val hoursText =
                pluralStringResource(R.plurals.countdown_hours, hours.toInt(), hours.toInt())
            val minutesText =
                pluralStringResource(R.plurals.countdown_minutes, minutes.toInt(), minutes.toInt())
            val secondsText =
                pluralStringResource(R.plurals.countdown_seconds, seconds.toInt(), seconds.toInt())
            countdownText =
                stringResource(R.string.countdown_full_today, hoursText, minutesText, secondsText)
        }

        else -> {
            val daysText =
                pluralStringResource(R.plurals.countdown_days, days.toInt(), days.toInt())
            val hoursText =
                pluralStringResource(R.plurals.countdown_hours, hours.toInt(), hours.toInt())
            val minutesText =
                pluralStringResource(R.plurals.countdown_minutes, minutes.toInt(), minutes.toInt())
            countdownText =
                stringResource(R.string.countdown_full_days, daysText, hoursText, minutesText)
        }
    }

    Text(
        modifier = modifier,
        text = countdownText,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.primary,
    )
}

@Preview
@Composable
private fun LaunchCardPreview() {
    SpacehubTheme {
        LaunchCard(
            title = "Falcon 9 Block",
            agency = "SpaceX",
            location = "Cape Canaveral, FL, USA",
            dateTime = "24 Lug ‘23 • 19:00",
            netMillis = System.currentTimeMillis() + 100000L,
            status = Status.Go(name = "Go", abbrev = "GO", description = "description"),
            launchImageUrl = "",
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun LaunchCardLongTitlePreview() {
    SpacehubTheme {
        LaunchCard(
            title = "SpaceX Starship TestFlight number 2",
            agency = "SpaceX",
            location = "San Giovanni Rotondo, Puglia (FG), 71013, Italy",
            dateTime = "24 Lug ‘23 • 19:00",
            netMillis = System.currentTimeMillis() + 100000L,
            status = Status.Go(name = "Go", abbrev = "GO", description = "description"),
            launchImageUrl = "",
            onClick = {},
        )
    }
}
