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
package com.lpirro.spacehub.core.composables

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.lpirro.spacehub.core.R
import com.lpirro.spacehub.core.ui.theme.SpacehubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpaceTopBar(text: String) {
    val ralewayFontFamily =
        FontFamily(
            Font(R.font.raleway_regular, FontWeight.Normal),
            Font(R.font.raleway_bold, FontWeight.Bold),
        )

    TopAppBar(
        navigationIcon = {
            Image(painter = painterResource(R.drawable.spacehub), contentDescription = "")
        },
        title = {
            Text(
                text = text,
                style =
                    TextStyle(
                        fontSize = 22.sp,
                        fontFamily = ralewayFontFamily,
                        fontWeight = FontWeight.Bold,
                    ),
            )
        },
    )
}

@Preview
@Composable
private fun SpaceTopBarPreview() {
    SpacehubTheme {
        SpaceTopBar(text = "SpaceHub")
    }
}
