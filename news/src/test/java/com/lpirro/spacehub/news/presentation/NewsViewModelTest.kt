package com.lpirro.spacehub.news.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.lpirro.spacehub.news.domain.model.Article
import com.lpirro.spacehub.news.domain.usecase.FilterNewsUseCase
import com.lpirro.spacehub.news.domain.usecase.GetNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val getNewsUseCase: GetNewsUseCase = mock()
    private val filterNewsUseCase: FilterNewsUseCase = mock()

    private lateinit var newsViewModel: NewsViewModel

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
    fun `getNews emits articles when use case returns data`() = runTest {
        val articles = Article.Mocks.articlesMocks

        whenever(getNewsUseCase()).thenReturn(flowOf(articles))

        newsViewModel = NewsViewModel(getNewsUseCase, filterNewsUseCase)

        newsViewModel.uiState.test {
            assertEquals(
                NewsUiState(isLoading = true),
                awaitItem(),
            )

            assertEquals(
                NewsUiState(articles = articles),
                awaitItem(),
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getNews emits error true when use case returns an error`() = runTest {
        whenever(getNewsUseCase()).thenReturn(
            flow { throw Exception("Network Error") },
        )

        newsViewModel = NewsViewModel(getNewsUseCase, filterNewsUseCase)

        newsViewModel.uiState.test {
            assertEquals(
                NewsUiState(isLoading = true),
                awaitItem(),
            )

            assertEquals(
                NewsUiState(error = true),
                awaitItem(),
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateSearchTextState emits search data when use case returns data`() = runTest {
        val articles = Article.Mocks.articlesMocks
        val searchResult = listOf(
            Article(
                id = 92,
                featured = false,
                publishedAt = "2024-09-30",
                imageUrl = "https://example.com/image1.jpg",
                newsSite = "Search Article",
                title = "Titled",
                url = "https://example.com/article1",
                launches = emptyList(),
                updatedAt = "2024-09-30",
                publishDateOffset = "5h ago",
            ),
        )

        val filterQuery = "SpaceX"
        whenever(getNewsUseCase()).thenReturn(flowOf(articles))
        whenever(filterNewsUseCase(filterQuery)).thenReturn(flowOf(searchResult))

        newsViewModel = NewsViewModel(getNewsUseCase, filterNewsUseCase)
        newsViewModel.updateSearchTextState(filterQuery)
        advanceUntilIdle()

        newsViewModel.uiState.test {
            assertEquals(
                NewsUiState(isLoading = true),
                awaitItem(),
            )

            assertEquals(
                NewsUiState(articles = searchResult),
                awaitItem(),
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateSearchTextState emits the searchWidgetState`() {
        newsViewModel = NewsViewModel(getNewsUseCase, filterNewsUseCase)

        newsViewModel.updateSearchTextState("new text")
        assertEquals(newsViewModel.searchTextState.value, "new text")
    }

    @Test
    fun `updateSearchWidgetState emits the searchWidgetState when articles are not empty`() {
        newsViewModel = NewsViewModel(getNewsUseCase, filterNewsUseCase)
        newsViewModel.articles = Article.Mocks.articlesMocks

        newsViewModel.updateSearchWidgetState(SearchWidgetState.OPENED)
        assertEquals(newsViewModel.searchWidgetState.value, SearchWidgetState.OPENED)
    }

    @Test
    fun `updateSearchWidgetState emits the searchWidgetState CLOSED when articles are empty`() {
        newsViewModel = NewsViewModel(getNewsUseCase, filterNewsUseCase)
        newsViewModel.articles = emptyList()

        // if the articles are empty and we try to open the search with SearchWidgetState.OPENED, the state stays to CLOSED
        // because we don't want to search
        newsViewModel.updateSearchWidgetState(SearchWidgetState.OPENED)
        assertEquals(newsViewModel.searchWidgetState.value, SearchWidgetState.CLOSED)
    }

    @Test
    fun `when SearchWidgetState is CLOSED and updateSearchWidgetState gets called then the original article list will be emitted`() = runTest {
        val articles = Article.Mocks.articlesMocks
        whenever(getNewsUseCase()).thenReturn(flowOf(articles))

        newsViewModel = NewsViewModel(getNewsUseCase, filterNewsUseCase)
        newsViewModel.articles = articles

        newsViewModel.updateSearchWidgetState(SearchWidgetState.CLOSED)
        assertEquals(newsViewModel.searchWidgetState.value, SearchWidgetState.CLOSED)

        newsViewModel.uiState.test {
            skipItems(1) // skip the loading state
            assertEquals(NewsUiState(articles = articles), awaitItem())
        }
    }

    @Test
    fun `filterNewsUseCase does NOT get called when the search query is less than 4 characters`() =
        runTest {
            newsViewModel = NewsViewModel(getNewsUseCase, filterNewsUseCase)
            val searchQuery = "spa"
            newsViewModel.updateSearchTextState(searchQuery)
            verify(filterNewsUseCase, never()).invoke(any())
        }

    @Test
    fun `filterNewsUseCase gets called when the search query is less more than 3 characters`() =
        runTest {
            newsViewModel = NewsViewModel(getNewsUseCase, filterNewsUseCase)
            val searchQuery = "spac"
            newsViewModel.updateSearchTextState(searchQuery)
            advanceUntilIdle()

            verify(filterNewsUseCase).invoke(searchQuery)
        }
}
