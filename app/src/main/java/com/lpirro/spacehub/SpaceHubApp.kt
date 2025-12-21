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
package com.lpirro.spacehub

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.spacehub.core.navigation.LaunchDetail
import com.spacehub.core.navigation.Launches
import com.spacehub.core.navigation.News
import com.spacehub.core.ui.composables.SpaceHubNavBar
import com.spacehub.core.ui.theme.SpacehubTheme
import com.spacehub.launches.presentation.LaunchesScreen
import com.spacehub.news.presentation.NewsScreen
import com.spacehub.launchdetail.presentation.LaunchDetailScreen

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
                        0 ->
                            navController.navigate(Launches) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                        1 ->
                            navController.navigate(News) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                    }
                },
            )
        },
    ) { innerPadding ->
        SpaceHubNavHost(
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            navController = navController,
        )
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
                onLaunchClicked = { id, name ->
                    navController.navigate(LaunchDetail(launchId = id, title = name))
                },
            )
        }
        composable<News> {
            val context = LocalContext.current
            NewsScreen(onArticleClick = { articleUrl ->
                val builder = CustomTabsIntent.Builder().build()
                builder.launchUrl(context, Uri.parse(articleUrl))
            })
        }
        composable<LaunchDetail> {
            val context = LocalContext.current
            val args = it.toRoute<LaunchDetail>()
            LaunchDetailScreen(
                launchId = args.launchId,
                title = args.title,
                onBackPressed = { navController.popBackStack() },
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
