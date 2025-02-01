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

package com.spacehub.launchdetail.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lpirro.spacehub.core.ui.composables.SpaceTopBar
import com.spacehub.launchdetail.R

@Composable
fun LaunchDetailScreen(
    onBackPressed: (() -> Unit)? = null,
) {
    Scaffold(
        topBar = {
            SpaceTopBar(
                text = stringResource(R.string.launch_detail_topbar_title),
                showBackArrow = true,
                onBackClick = onBackPressed
            )
        }
    ) { innerPadding ->

    }
}