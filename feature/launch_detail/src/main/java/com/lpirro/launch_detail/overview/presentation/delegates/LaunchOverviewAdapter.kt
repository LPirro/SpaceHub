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
package com.lpirro.launch_detail.overview.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.lpirro.launch_detail.overview.model.LaunchOverviewItem

class LaunchOverviewAdapter(
    addToCalendarListener: (launchName: String, launchDateMillis: Long) -> Unit,
    addToSavedListener: (launchId: String) -> Unit,
    removeFromSavedListener: (launchId: String) -> Unit,
    launchTrajectoryListener: (String) -> Unit,
    mapsClickListener: (String) -> Unit,
    wikipediaClickListener: (String) -> Unit,
    infoClickListener: (String) -> Unit,
    fullScreenClickListener: (String) -> Unit
) : AsyncListDifferDelegationAdapter<LaunchOverviewItem>(LaunchOverviewDiffCallback) {
    init {
        delegatesManager
            .addDelegate(pastLaunchHeaderDelegate(removeFromSavedListener))
            .addDelegate(
                countDownHeaderDelegate(
                    addToCalendarListener,
                    addToSavedListener,
                    removeFromSavedListener
                )
            )

        delegatesManager.addDelegate(watchLiveDelegate(fullScreenClickListener))
        delegatesManager.addDelegate(agencyDelegate())
        delegatesManager.addDelegate(locationDelegate())
        delegatesManager.addDelegate(trajectoryDelegate(launchTrajectoryListener))
        delegatesManager.addDelegate(
            launchpadDelegate(
                mapsClickListener,
                wikipediaClickListener,
                infoClickListener
            )
        )
    }
}
