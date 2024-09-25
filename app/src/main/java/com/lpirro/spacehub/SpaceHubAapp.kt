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

package com.lpirro.spacehub

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lpirro.spacehub.core.composables.SpaceHubNavBar
import com.lpirro.spacehub.core.navigation.Launches
import com.lpirro.spacehub.core.ui.theme.SpacehubTheme
import com.lpirro.spacehub.launches.presentation.LaunchesScreen

@Composable
fun SpaceHubApp() {

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            SpaceHubNavBar(
                selectedItemIndex = selectedItemIndex,
                onClick = {
                    selectedItemIndex = it
                    when (selectedItemIndex) {
                        0 -> navController.navigate(Launches) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                    }
                }
            )
        },
    ) { innerPadding ->
        SpaceHubNavHost(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()), navController = navController)
    }
}

@Composable
fun SpaceHubNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Launches,
    ) {
        composable<Launches> {
            LaunchesScreen(
                onLaunchClicked = {
                    // TODO: Implement navigation to the detail screen
                },
            )
        }
    }
}

@Composable
@Preview
fun SpaceHubAppPreview() {
    SpacehubTheme {
        SpaceHubApp()
    }
}