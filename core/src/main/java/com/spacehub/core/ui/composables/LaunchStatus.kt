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
package com.spacehub.core.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spacehub.common.models.domain.Status
import com.spacehub.core.ui.theme.SpacehubTheme

@Composable
fun LaunchStatus(
    modifier: Modifier = Modifier,
    status: Status,
) {
    var backgroundColor: Color = MaterialTheme.colorScheme.primary
    var textColor: Color = MaterialTheme.colorScheme.onPrimary

    when (status) {
        is Status.Failure -> {
            textColor = SpacehubTheme.colors.danger
            backgroundColor = Color(0xFF4B191A)
        }

        is Status.Go, is Status.Success -> {
            textColor = SpacehubTheme.colors.success
            backgroundColor = Color(0xFF194B20)
        }

        is Status.TBC  -> {
            textColor = SpacehubTheme.colors.warning
            backgroundColor = Color(0xFF4B2E19)
        }

        is Status.InFlight -> {
            textColor = MaterialTheme.colorScheme.onPrimaryContainer
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
        }

        is Status.TBD, is Status.Unknown -> {
            textColor = MaterialTheme.colorScheme.onSurfaceVariant
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant
        }
    }

    Box(
        modifier =
        modifier
            .clip(RoundedCornerShape(4.dp))
            .height(20.dp)
            .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 6.dp),
            text = status.abbrev,
            color = textColor,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Preview(group = "Failure", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(group = "Failure")
@Composable
private fun LaunchStatusFailurePreview() {
    SpacehubTheme {
        LaunchStatus(
            status =
            Status.Failure(
                name = "Failure",
                abbrev = "Failure",
                description = "description",
            ),
        )
    }
}

@Preview(group = "Go", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(group = "Go")
@Composable
private fun LaunchStatusGoPreview() {
    SpacehubTheme {
        LaunchStatus(
            status = Status.Go(name = "Go", abbrev = "GO", description = "description"),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, group = "Success")
@Preview(group = "Success")
@Composable
private fun LaunchStatusSuccessPreview() {
    SpacehubTheme {
        LaunchStatus(
            status =
            Status.Success(
                name = "Success",
                abbrev = "Success",
                description = "description",
            ),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, group = "In Flight")
@Preview(group = "In Flight")
@Composable
private fun LaunchStatusInFlightPreview() {
    SpacehubTheme {
        LaunchStatus(
            status =
            Status.InFlight(
                name = "In Flight",
                abbrev = "In Flight",
                description = "description",
            ),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, group = "TBD")
@Preview(group = "TBD")
@Composable
private fun LaunchStatusTBDPreview() {
    SpacehubTheme {
        LaunchStatus(
            status = Status.TBD(name = "TBD", abbrev = "TBD", description = "description"),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, group = "TBC")
@Preview(group = "TBC")
@Composable
private fun LaunchStatusTBCPreview() {
    SpacehubTheme {
        LaunchStatus(
            status = Status.TBC(name = "TBC", abbrev = "TBC", description = "description"),
        )
    }
}
