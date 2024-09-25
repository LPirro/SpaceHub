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

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.lpirro.spacehub.core.R
import com.lpirro.spacehub.core.ui.theme.SpacehubTheme

data class BottomNavigationItem(
    val title: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
)

@Composable
fun SpaceHubNavBar(
    selectedItemIndex: Int,
    onClick: (index: Int) -> Unit,
) {
    val items =
        listOf(
            BottomNavigationItem(
                title = stringResource(R.string.nav_bar_launches),
                selectedIcon = R.drawable.rocket_launch,
                unselectedIcon = R.drawable.rocket_launch_outline,
            ),
            BottomNavigationItem(
                title = stringResource(R.string.nav_bar_news),
                selectedIcon = R.drawable.newspaper_variant,
                unselectedIcon = R.drawable.newspaper_variant_outline,
            ),
            BottomNavigationItem(
                title = stringResource(R.string.nav_bar_saved_launches),
                selectedIcon = R.drawable.calendar_heart,
                unselectedIcon = R.drawable.calendar_heart_outline,
            ),
        )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = { onClick.invoke(index) },
                label = {
                    Text(
                        text = item.title,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                icon = {
                    Icon(
                        painter =
                        if (index == selectedItemIndex) {
                            painterResource(id = item.selectedIcon)
                        } else {
                            painterResource(id = item.unselectedIcon)
                        },
                        contentDescription = item.title,
                        tint =
                        if (selectedItemIndex == index) {
                            MaterialTheme.colorScheme.onSecondaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                    )
                },
            )
        }
    }
}

@Preview
@Composable
private fun SpaceHubNavBarPreview() {
    SpacehubTheme {
        SpaceHubNavBar(
            selectedItemIndex = 0,
            onClick = {},
        )
    }
}
