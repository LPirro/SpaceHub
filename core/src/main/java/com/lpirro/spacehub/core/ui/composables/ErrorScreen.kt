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

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lpirro.spacehub.core.R
import com.lpirro.spacehub.core.ui.theme.SpacehubTheme

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    onTryAgainClicked: () -> Unit,
) {
    Surface(modifier.fillMaxSize()) {
        Column(
            modifier = modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.astronaut_fall),
                contentDescription = stringResource(R.string.cd_error_screen_image),
                modifier = Modifier.size(height = 160.dp, width = 184.dp),
            )

            Spacer(Modifier.padding(top = 32.dp))

            Text(
                text = stringResource(R.string.error_screen_title),
                style = MaterialTheme.typography.headlineSmall,
            )

            Spacer(Modifier.padding(top = 10.dp))

            Text(
                textAlign = TextAlign.Center,
                text = stringResource(R.string.error_screen_description),
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(Modifier.padding(top = 46.dp))

            Button(
                onClick = onTryAgainClicked,
            ) {
                Text(text = stringResource(R.string.error_screen_try_again))
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorScreenComposable() {
    SpacehubTheme {
        ErrorScreen(onTryAgainClicked = {})
    }
}
