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
package com.spacehub.launches.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.spacehub.core.result.DataError
import com.spacehub.core.result.Result
import com.spacehub.launches.domain.usecase.GetPastLaunchesUseCase
import com.spacehub.launches.domain.usecase.GetUpcomingLaunchesUseCase
import com.spacehub.launches.mocks.MockLaunchUi
import com.spacehub.launches.presentation.mapper.LaunchUiMapper
import com.spacehub.testutil.mocks.MockLaunch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class LaunchesViewModelTest {
    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val getUpcomingLaunchesUseCase: GetUpcomingLaunchesUseCase = mock()
    private val getPastLaunchesUseCase: GetPastLaunchesUseCase = mock()
    private val launchUiMapper: LaunchUiMapper = mock()

    private lateinit var launchesViewModel: LaunchesViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUpcomingLaunches emits Success when use case returns data`() =
        runTest {
            val launches = listOf(MockLaunch.create())
            whenever(getUpcomingLaunchesUseCase()).thenReturn(flowOf(Result.Success(launches)))
            whenever(launchUiMapper.mapToUi(any())).thenReturn(MockLaunchUi.create())

            launchesViewModel =
                LaunchesViewModel(
                    getUpcomingLaunchesUseCase,
                    getPastLaunchesUseCase,
                    launchUiMapper,
                )

            launchesViewModel.uiStateUpcomingLaunches.test {
                Assert.assertEquals(
                    LaunchesUiState.Loading(true),
                    awaitItem(),
                )

                Assert.assertEquals(
                    LaunchesUiState.Success(listOf(MockLaunchUi.create())),
                    awaitItem(),
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getUpcomingLaunches emits Error when use case throws exception`() =
        runTest {
            whenever(getUpcomingLaunchesUseCase()).thenReturn(
                flowOf(Result.Error(DataError.Network(message = "Network Error"))),
            )

            launchesViewModel =
                LaunchesViewModel(
                    getUpcomingLaunchesUseCase,
                    getPastLaunchesUseCase,
                    launchUiMapper,
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
            val launches = listOf(MockLaunch.create())
            whenever(getPastLaunchesUseCase()).thenReturn(flowOf(Result.Success(launches)))
            whenever(launchUiMapper.mapToUi(any())).thenReturn(MockLaunchUi.create())

            launchesViewModel =
                LaunchesViewModel(
                    getUpcomingLaunchesUseCase,
                    getPastLaunchesUseCase,
                    launchUiMapper,
                )

            launchesViewModel.uiStatePastLaunches.test {
                Assert.assertEquals(
                    LaunchesUiState.Loading(true),
                    awaitItem(),
                )

                Assert.assertEquals(
                    LaunchesUiState.Success(listOf(MockLaunchUi.create())),
                    awaitItem(),
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getPastLaunches emits Error when use case throws exception`() =
        runTest {
            whenever(getPastLaunchesUseCase()).thenReturn(
                flowOf(Result.Error(DataError.Network(message = "Network Error"))),
            )

            launchesViewModel =
                LaunchesViewModel(
                    getUpcomingLaunchesUseCase,
                    getPastLaunchesUseCase,
                    launchUiMapper,
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
                    MockLaunch.create(),
                )
            whenever(getUpcomingLaunchesUseCase(any())).thenReturn(flowOf(Result.Success(launches)))
            whenever(getPastLaunchesUseCase()).thenReturn(flowOf(Result.Success(launches)))

            launchesViewModel =
                LaunchesViewModel(
                    getUpcomingLaunchesUseCase,
                    getPastLaunchesUseCase,
                    launchUiMapper,
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
