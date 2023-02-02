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

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.lpirro.core.extensions.hide
import com.lpirro.core.extensions.show
import com.lpirro.core.ui.NavDrawerInteraction
import com.lpirro.spacehub.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener, NavDrawerInteraction {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    // TODO Will Be Removed as part of SH-55
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) { } else { }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawer = binding.drawerLayout
        navController = findNavController(R.id.navHost)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.launches_graph,
                R.id.news_graph,
                R.id.saved_launches_graph,
            ),
            drawer
        )
        navController.addOnDestinationChangedListener(this)
        setupNavigation()

        // TODO Will Be Removed as part of SH-55
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) ||
            super.onSupportNavigateUp()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        findNavController(R.id.navHost).handleDeepLink(intent)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            com.lpirro.core.R.id.navigation_launch_detail,
            com.lpirro.core.R.id.navigation_astronauts -> {
                binding.bottomNavigation.hide()
            }
            else -> {
                binding.bottomNavigation.show()
            }
        }
    }

    override fun openDrawer() {
        binding.drawerLayout.open()
    }

    override fun closeDrawer() {
        binding.drawerLayout.close()
    }

    private fun setupNavigation() {
        binding.bottomNavigation.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)
    }
}
