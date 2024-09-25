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
package com.lpirro.spacehub.launches.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.lpirro.spacehub.launches.domain.usecase.GetPastLaunchesUseCase
import com.lpirro.spacehub.launches.domain.usecase.GetUpcomingLaunchesUseCase
import com.lpirro.spacehub.launches.util.MockedLaunches
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class LaunchesViewModelTest {
    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val getUpcomingLaunchesUseCase: GetUpcomingLaunchesUseCase = mock()
    private val getPastLaunchesUseCase: GetPastLaunchesUseCase = mock()
    private lateinit var launchesViewModel: LaunchesViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUpcomingLaunches emits Success when use case returns data`() =
        runTest {
            val launches =
                listOf(
                    MockedLaunches.fakeLaunch,
                )
            whenever(getUpcomingLaunchesUseCase()).thenReturn(flowOf(launches))

            launchesViewModel =
                LaunchesViewModel(
                    getUpcomingLaunchesUseCase,
                    getPastLaunchesUseCase,
                )

            launchesViewModel.uiStateUpcomingLaunches.test {
                Assert.assertEquals(
                    LaunchesUiState.Loading(true),
                    awaitItem(),
                )

                Assert.assertEquals(
                    LaunchesUiState.Success(launches),
                    awaitItem(),
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getUpcomingLaunches emits Error when use case throws exception`() =
        runTest {
            whenever(getUpcomingLaunchesUseCase()).thenReturn(
                flow { throw Exception("Network Error") },
            )

            launchesViewModel =
                LaunchesViewModel(
                    getUpcomingLaunchesUseCase,
                    getPastLaunchesUseCase,
                )

            launchesViewModel.uiStateUpcomingLaunches.test {
                Assert.assertEquals(
                    LaunchesUiState.Loading(true),
                    awaitItem(),
                )

                Assert.assertEquals(
                    LaunchesUiState.Error,
                    awaitItem(),
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getPastLaunches emits Success when use case returns data`() =
        runTest {
            val launches =
                listOf(
                    MockedLaunches.fakeLaunch,
                )
            whenever(getPastLaunchesUseCase()).thenReturn(flowOf(launches))

            launchesViewModel =
                LaunchesViewModel(
                    getUpcomingLaunchesUseCase,
                    getPastLaunchesUseCase,
                )

            launchesViewModel.uiStatePastLaunches.test {
                Assert.assertEquals(
                    LaunchesUiState.Loading(true),
                    awaitItem(),
                )

                Assert.assertEquals(
                    LaunchesUiState.Success(launches),
                    awaitItem(),
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getPastLaunches emits Error when use case throws exception`() =
        runTest {
            whenever(getPastLaunchesUseCase()).thenReturn(
                flow { throw Exception("Network Error") },
            )

            launchesViewModel =
                LaunchesViewModel(
                    getUpcomingLaunchesUseCase,
                    getPastLaunchesUseCase,
                )

            launchesViewModel.uiStatePastLaunches.test {
                Assert.assertEquals(
                    LaunchesUiState.Loading(true),
                    awaitItem(),
                )

                Assert.assertEquals(
                    LaunchesUiState.Error,
                    awaitItem(),
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `isRefreshLoading is true during refresh and false after completion`() =
        runTest {
            val launches =
                listOf(
                    MockedLaunches.fakeLaunch,
                )
            whenever(getUpcomingLaunchesUseCase()).thenReturn(flowOf(launches))

            launchesViewModel =
                LaunchesViewModel(
                    getUpcomingLaunchesUseCase,
                    getPastLaunchesUseCase,
                )

            launchesViewModel.isRefreshLoading.test {
                Assert.assertFalse(awaitItem())

                launchesViewModel.getUpcomingLaunches(isRefresh = true)
                testDispatcher.scheduler.advanceUntilIdle()

                Assert.assertTrue(awaitItem())

                Assert.assertFalse(awaitItem())

                cancelAndIgnoreRemainingEvents()
            }
        }
}
