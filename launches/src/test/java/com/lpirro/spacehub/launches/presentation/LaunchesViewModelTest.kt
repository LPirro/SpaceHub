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
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.kotlin.*

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
    fun `getUpcomingLaunches emits Success when use case returns data`() = runTest {
        val launches = listOf(
            MockedLaunches.fakeLaunch
        )
        whenever(getUpcomingLaunchesUseCase()).thenReturn(flowOf(launches))

        launchesViewModel = LaunchesViewModel(
            getUpcomingLaunchesUseCase,
            getPastLaunchesUseCase
        )

        launchesViewModel.uiStateUpcomingLaunches.test {
            Assert.assertEquals(
                LaunchesUiState.Loading(true),
                awaitItem()
            )

            Assert.assertEquals(
                LaunchesUiState.Success(launches),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getUpcomingLaunches emits Error when use case throws exception`() = runTest {
        whenever(getUpcomingLaunchesUseCase()).thenReturn(
            flow { throw Exception("Network Error") }
        )

        launchesViewModel = LaunchesViewModel(
            getUpcomingLaunchesUseCase,
            getPastLaunchesUseCase
        )

        launchesViewModel.uiStateUpcomingLaunches.test {
            Assert.assertEquals(
                LaunchesUiState.Loading(true),
                awaitItem()
            )

            Assert.assertEquals(
                LaunchesUiState.Error,
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getPastLaunches emits Success when use case returns data`() = runTest {
        val launches = listOf(
            MockedLaunches.fakeLaunch
        )
        whenever(getPastLaunchesUseCase()).thenReturn(flowOf(launches))

        launchesViewModel = LaunchesViewModel(
            getUpcomingLaunchesUseCase,
            getPastLaunchesUseCase
        )

        launchesViewModel.uiStatePastLaunches.test {
            Assert.assertEquals(
                LaunchesUiState.Loading(true),
                awaitItem()
            )

            Assert.assertEquals(
                LaunchesUiState.Success(launches),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getPastLaunches emits Error when use case throws exception`() = runTest {
        whenever(getPastLaunchesUseCase()).thenReturn(
            flow { throw Exception("Network Error") }
        )

        launchesViewModel = LaunchesViewModel(
            getUpcomingLaunchesUseCase,
            getPastLaunchesUseCase
        )

        launchesViewModel.uiStatePastLaunches.test {
            Assert.assertEquals(
                LaunchesUiState.Loading(true),
                awaitItem()
            )

            Assert.assertEquals(
                LaunchesUiState.Error,
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `isRefreshLoading is true during refresh and false after completion`() = runTest {
        val launches = listOf(
            MockedLaunches.fakeLaunch
        )
        whenever(getUpcomingLaunchesUseCase()).thenReturn(flowOf(launches))

        launchesViewModel = LaunchesViewModel(
            getUpcomingLaunchesUseCase,
            getPastLaunchesUseCase
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