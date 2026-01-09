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
import com.spacehub.common.domain.repository.PagedLaunches
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
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class LaunchesViewModelTest {
    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val getUpcomingLaunchesUseCase: GetUpcomingLaunchesUseCase = mock()
    private val getPastLaunchesUseCase: GetPastLaunchesUseCase = mock()
    private val launchUiMapper: LaunchUiMapper = mock()

    private lateinit var viewModel: LaunchesViewModel

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
    fun `init loads launches and emits Success when both use cases return data`() =
        runTest {
            val upcomingLaunches = listOf(MockLaunch.create(), MockLaunch.create())
            val pastLaunches = listOf(MockLaunch.create())
            val mockLaunchUi = MockLaunchUi.create()

            whenever(getUpcomingLaunchesUseCase(forceRefresh = false))
                .thenReturn(flowOf(Result.Success(PagedLaunches(upcomingLaunches, hasNextPage = false))))
            whenever(getPastLaunchesUseCase(forceRefresh = false))
                .thenReturn(flowOf(Result.Success(PagedLaunches(pastLaunches, hasNextPage = false))))
            whenever(launchUiMapper.mapToUi(any())).thenReturn(mockLaunchUi)

            viewModel = LaunchesViewModel(
                getUpcomingLaunchesUseCase,
                getPastLaunchesUseCase,
                launchUiMapper,
            )

            viewModel.uiState.test {
                assertEquals(
                    LaunchesUiState.Loading(true),
                    awaitItem(),
                )

                advanceUntilIdle()

                val successState = awaitItem()
                assertTrue(successState is LaunchesUiState.Success)
                assertEquals(2, (successState as LaunchesUiState.Success).upcomingLaunches.size)
                assertEquals(1, successState.pastLaunches.size)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `init emits Error when upcoming launches use case fails`() =
        runTest {
            val pastLaunches = listOf(MockLaunch.create())

            whenever(getUpcomingLaunchesUseCase(forceRefresh = false))
                .thenReturn(flowOf(Result.Error(DataError.Network(message = "Network Error"))))
            whenever(getPastLaunchesUseCase(forceRefresh = false))
                .thenReturn(flowOf(Result.Success(PagedLaunches(pastLaunches, hasNextPage = false))))

            viewModel = LaunchesViewModel(
                getUpcomingLaunchesUseCase,
                getPastLaunchesUseCase,
                launchUiMapper,
            )

            viewModel.uiState.test {
                assertEquals(
                    LaunchesUiState.Loading(true),
                    awaitItem(),
                )

                advanceUntilIdle()

                assertEquals(
                    LaunchesUiState.Error,
                    awaitItem(),
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `init emits Error when past launches use case fails`() =
        runTest {
            val upcomingLaunches = listOf(MockLaunch.create())

            whenever(getUpcomingLaunchesUseCase(forceRefresh = false))
                .thenReturn(flowOf(Result.Success(PagedLaunches(upcomingLaunches, hasNextPage = false))))
            whenever(getPastLaunchesUseCase(forceRefresh = false))
                .thenReturn(flowOf(Result.Error(DataError.Network(message = "Network Error"))))

            viewModel = LaunchesViewModel(
                getUpcomingLaunchesUseCase,
                getPastLaunchesUseCase,
                launchUiMapper,
            )

            viewModel.uiState.test {
                assertEquals(
                    LaunchesUiState.Loading(true),
                    awaitItem(),
                )

                advanceUntilIdle()

                assertEquals(
                    LaunchesUiState.Error,
                    awaitItem(),
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `init emits Error when both use cases fail`() =
        runTest {
            whenever(getUpcomingLaunchesUseCase(forceRefresh = false))
                .thenReturn(flowOf(Result.Error(DataError.Network(message = "Network Error"))))
            whenever(getPastLaunchesUseCase(forceRefresh = false))
                .thenReturn(flowOf(Result.Error(DataError.Network(message = "Network Error"))))

            viewModel = LaunchesViewModel(
                getUpcomingLaunchesUseCase,
                getPastLaunchesUseCase,
                launchUiMapper,
            )

            viewModel.uiState.test {
                assertEquals(
                    LaunchesUiState.Loading(true),
                    awaitItem(),
                )

                advanceUntilIdle()

                assertEquals(
                    LaunchesUiState.Error,
                    awaitItem(),
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getLaunches with isRefresh true does not emit Loading state`() =
        runTest {
            val launches = listOf(MockLaunch.create())
            val refreshedLaunches = listOf(MockLaunch.create(), MockLaunch.create())
            val mockLaunchUi = MockLaunchUi.create()
            val mockLaunchUiRefreshed = MockLaunchUi.create().copy(id = "launch-002")

            whenever(getUpcomingLaunchesUseCase(forceRefresh = false))
                .thenReturn(flowOf(Result.Success(PagedLaunches(launches, hasNextPage = false))))
            whenever(getPastLaunchesUseCase(forceRefresh = false))
                .thenReturn(flowOf(Result.Success(PagedLaunches(launches, hasNextPage = false))))

            whenever(getUpcomingLaunchesUseCase(forceRefresh = true))
                .thenReturn(flowOf(Result.Success(PagedLaunches(refreshedLaunches, hasNextPage = false))))
            whenever(getPastLaunchesUseCase(forceRefresh = true))
                .thenReturn(flowOf(Result.Success(PagedLaunches(refreshedLaunches, hasNextPage = false))))

            whenever(launchUiMapper.mapToUi(launches[0])).thenReturn(mockLaunchUi)
            whenever(launchUiMapper.mapToUi(refreshedLaunches[0])).thenReturn(mockLaunchUiRefreshed)
            whenever(launchUiMapper.mapToUi(refreshedLaunches[1])).thenReturn(mockLaunchUiRefreshed)

            viewModel = LaunchesViewModel(
                getUpcomingLaunchesUseCase,
                getPastLaunchesUseCase,
                launchUiMapper,
            )

            viewModel.uiState.test {
                assertEquals(
                    LaunchesUiState.Loading(true),
                    awaitItem(),
                )

                advanceUntilIdle()

                val initialState = awaitItem()
                assertTrue(initialState is LaunchesUiState.Success)
                assertEquals(1, (initialState as LaunchesUiState.Success).upcomingLaunches.size)

                viewModel.getLaunches(isRefresh = true)
                advanceUntilIdle()

                val nextState = awaitItem()
                assertTrue(nextState is LaunchesUiState.Success)
                assertEquals(2, (nextState as LaunchesUiState.Success).upcomingLaunches.size)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getLaunches with isRefresh false emits Loading state`() =
        runTest {
            val launches = listOf(MockLaunch.create())
            val mockLaunchUi = MockLaunchUi.create()

            whenever(getUpcomingLaunchesUseCase(any()))
                .thenReturn(flowOf(Result.Success(PagedLaunches(launches, hasNextPage = false))))
            whenever(getPastLaunchesUseCase(any()))
                .thenReturn(flowOf(Result.Success(PagedLaunches(launches, hasNextPage = false))))
            whenever(launchUiMapper.mapToUi(any())).thenReturn(mockLaunchUi)

            viewModel = LaunchesViewModel(
                getUpcomingLaunchesUseCase,
                getPastLaunchesUseCase,
                launchUiMapper,
            )

            viewModel.uiState.test {
                assertEquals(
                    LaunchesUiState.Loading(true),
                    awaitItem(),
                )

                advanceUntilIdle()

                val initialState = awaitItem()
                assertTrue(initialState is LaunchesUiState.Success)

                viewModel.getLaunches(isRefresh = false)
                testDispatcher.scheduler.runCurrent()

                assertEquals(
                    LaunchesUiState.Loading(true),
                    awaitItem(),
                )

                advanceUntilIdle()

                assertTrue(awaitItem() is LaunchesUiState.Success)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `isRefreshLoading is true during refresh and false after completion`() =
        runTest {
            val launches = listOf(MockLaunch.create())
            val mockLaunchUi = MockLaunchUi.create()

            whenever(getUpcomingLaunchesUseCase(any()))
                .thenReturn(flowOf(Result.Success(PagedLaunches(launches, hasNextPage = false))))
            whenever(getPastLaunchesUseCase(any()))
                .thenReturn(flowOf(Result.Success(PagedLaunches(launches, hasNextPage = false))))
            whenever(launchUiMapper.mapToUi(any())).thenReturn(mockLaunchUi)

            viewModel = LaunchesViewModel(
                getUpcomingLaunchesUseCase,
                getPastLaunchesUseCase,
                launchUiMapper,
            )

            advanceUntilIdle()

            viewModel.isRefreshLoading.test {
                assertFalse(awaitItem())

                viewModel.getLaunches(isRefresh = true)

                assertTrue(awaitItem())

                advanceUntilIdle()

                assertFalse(awaitItem())

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `refresh calls getLaunches with isRefresh true`() =
        runTest {
            val launches = listOf(MockLaunch.create())
            val mockLaunchUi = MockLaunchUi.create()

            whenever(getUpcomingLaunchesUseCase(any()))
                .thenReturn(flowOf(Result.Success(PagedLaunches(launches, hasNextPage = false))))
            whenever(getPastLaunchesUseCase(any()))
                .thenReturn(flowOf(Result.Success(PagedLaunches(launches, hasNextPage = false))))
            whenever(launchUiMapper.mapToUi(any())).thenReturn(mockLaunchUi)

            viewModel = LaunchesViewModel(
                getUpcomingLaunchesUseCase,
                getPastLaunchesUseCase,
                launchUiMapper,
            )

            advanceUntilIdle()

            viewModel.refresh()
            advanceUntilIdle()

            verify(getUpcomingLaunchesUseCase).invoke(forceRefresh = eq(true))
            verify(getPastLaunchesUseCase).invoke(forceRefresh = eq(true))
        }

    @Test
    fun `isRefreshLoading is false when refresh completes with error`() =
        runTest {
            val launches = listOf(MockLaunch.create())
            val mockLaunchUi = MockLaunchUi.create()

            whenever(getUpcomingLaunchesUseCase(forceRefresh = false))
                .thenReturn(flowOf(Result.Success(PagedLaunches(launches, hasNextPage = false))))
            whenever(getPastLaunchesUseCase(forceRefresh = false))
                .thenReturn(flowOf(Result.Success(PagedLaunches(launches, hasNextPage = false))))
            whenever(launchUiMapper.mapToUi(any())).thenReturn(mockLaunchUi)

            whenever(getUpcomingLaunchesUseCase(forceRefresh = true))
                .thenReturn(flowOf(Result.Error(DataError.Network(message = "Network Error"))))
            whenever(getPastLaunchesUseCase(forceRefresh = true))
                .thenReturn(flowOf(Result.Success(PagedLaunches(launches, hasNextPage = false))))

            viewModel = LaunchesViewModel(
                getUpcomingLaunchesUseCase,
                getPastLaunchesUseCase,
                launchUiMapper,
            )

            advanceUntilIdle()

            viewModel.isRefreshLoading.test {
                assertFalse(awaitItem())

                viewModel.refresh()

                assertTrue(awaitItem())

                advanceUntilIdle()

                assertFalse(awaitItem())

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `getLaunches calls use cases with correct forceRefresh parameter`() =
        runTest {
            val launches = listOf(MockLaunch.create())
            val mockLaunchUi = MockLaunchUi.create()

            whenever(getUpcomingLaunchesUseCase(any()))
                .thenReturn(flowOf(Result.Success(PagedLaunches(launches, hasNextPage = false))))
            whenever(getPastLaunchesUseCase(any()))
                .thenReturn(flowOf(Result.Success(PagedLaunches(launches, hasNextPage = false))))
            whenever(launchUiMapper.mapToUi(any())).thenReturn(mockLaunchUi)

            viewModel = LaunchesViewModel(
                getUpcomingLaunchesUseCase,
                getPastLaunchesUseCase,
                launchUiMapper,
            )

            advanceUntilIdle()

            verify(getUpcomingLaunchesUseCase).invoke(forceRefresh = eq(false))
            verify(getPastLaunchesUseCase).invoke(forceRefresh = eq(false))
        }
}