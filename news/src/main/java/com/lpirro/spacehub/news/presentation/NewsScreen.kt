@file:OptIn(ExperimentalMaterial3Api::class)

package com.lpirro.spacehub.news.presentation

import android.util.Log
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lpirro.spacehub.core.ui.composables.ErrorScreen
import com.lpirro.spacehub.core.ui.composables.SearchAppBar
import com.lpirro.spacehub.core.ui.composables.SpaceTopBar
import com.lpirro.spacehub.core.ui.theme.SpacehubTheme
import com.lpirro.spacehub.news.R
import com.lpirro.spacehub.news.domain.model.Article

@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel(), onArticleClick: (url: String) -> Unit) {
    val uiState = viewModel.uiState.collectAsState()
    val searchWidgetState by viewModel.searchWidgetState
    val searchTextState by viewModel.searchTextState
    val searchLoadingState by viewModel.searchLoadingState

    Scaffold(
        topBar = {
            SpaceSearchTopBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = { viewModel.updateSearchTextState(searchQuery = it) },
                onCloseClicked = {
                    viewModel.updateSearchTextState(searchQuery = "")
                    viewModel.updateSearchWidgetState(newState = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    Log.d("Search Text", it)
                },
                onSearchTriggered = {
                    viewModel.updateSearchWidgetState(newState = SearchWidgetState.OPENED)
                },
                isLoading = searchLoadingState,
            )
        },
    ) { innerPadding ->
        NewsScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            uiState = uiState.value,
            onRefresh = { viewModel.getNews(isRefresh = true) },
            onTryAgainClicked = viewModel::getNews,
            onArticleClick = onArticleClick,
        )
    }
}

@Composable
fun NewsScreen(
    modifier: Modifier,
    uiState: NewsUiState,
    onRefresh: () -> Unit,
    onArticleClick: (url: String) -> Unit,
    onTryAgainClicked: () -> Unit,
) {
    when {
        uiState.isLoading -> {
            CircularProgressIndicator(
                modifier
                    .fillMaxSize()
                    .wrapContentSize(),
            )
        }

        uiState.articles.isNotEmpty() -> {
            ArticleList(
                modifier = modifier,
                articles = uiState.articles,
                isRefreshing = uiState.isRefresh,
                onArticleClick = onArticleClick,
                onRefresh = onRefresh,
            )
        }

        uiState.error -> {
            ErrorScreen(modifier = modifier, onTryAgainClicked = onTryAgainClicked)
        }

        uiState.searchError ->
            Toast.makeText(
                LocalContext.current,
                stringResource(R.string.search_error_message),
                Toast.LENGTH_SHORT,
            ).show()
    }
}

@Composable
fun ArticleList(
    modifier: Modifier,
    articles: List<Article>,
    isRefreshing: Boolean,
    onArticleClick: (url: String) -> Unit,
    onRefresh: () -> Unit,
) {
    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn {
            items(articles) { article ->
                if (article.featured) {
                    ArticleBig(
                        articleInfo = "${article.newsSite} • ${article.publishDateOffset}",
                        title = article.title,
                        articleImageUrl = article.imageUrl,
                        articleUrl = article.url,
                        onArticleClick = onArticleClick,
                    )
                } else {
                    ArticleSmall(
                        articleInfo = "${article.newsSite} • ${article.publishDateOffset}",
                        title = article.title,
                        articleImageUrl = article.imageUrl,
                        articleUrl = article.url,
                        onArticleClick = onArticleClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun ArticleSmall(
    title: String,
    articleInfo: String,
    articleImageUrl: String,
    articleUrl: String,
    onArticleClick: (url: String) -> Unit,
) {
    val placeholderDrawable =
        AppCompatResources.getDrawable(
            LocalContext.current,
            com.lpirro.spacehub.core.R.drawable.image_placeholder,
        )
    placeholderDrawable?.setTint(MaterialTheme.colorScheme.inverseOnSurface.toArgb())

    Row(
        modifier = Modifier
            .clickable { onArticleClick.invoke(articleUrl) }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            ArticleInfo(articleInfo)
            Spacer(Modifier.height(2.dp))
            Text(
                modifier = Modifier.padding(end = 12.dp),
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
        }

        AsyncImage(
            model =
            ImageRequest.Builder(LocalContext.current)
                .data(articleImageUrl)
                .placeholder(placeholderDrawable)
                .error(placeholderDrawable)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .size(100.dp),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
    }
}

@Composable
private fun ArticleBig(
    title: String,
    articleInfo: String,
    articleImageUrl: String,
    articleUrl: String,
    onArticleClick: (url: String) -> Unit,
) {
    val placeholderDrawable =
        AppCompatResources.getDrawable(
            LocalContext.current,
            com.lpirro.spacehub.core.R.drawable.image_placeholder,
        )
    placeholderDrawable?.setTint(MaterialTheme.colorScheme.inverseOnSurface.toArgb())

    Column(
        modifier = Modifier
            .clickable { onArticleClick.invoke(articleUrl) }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        AsyncImage(
            model =
            ImageRequest.Builder(LocalContext.current)
                .data(articleImageUrl)
                .placeholder(placeholderDrawable)
                .error(placeholderDrawable)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .height(190.dp),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        Column {
            Spacer(Modifier.height(12.dp))
            ArticleInfo(articleInfo)
            Spacer(Modifier.height(2.dp))
            Text(
                modifier = Modifier.padding(end = 12.dp),
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun ArticleInfo(
    articleInfo: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(10.dp),
            painter = painterResource(com.lpirro.spacehub.core.R.drawable.newspaper_variant_outline),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            contentDescription = null,
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = articleInfo,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
fun SpaceSearchTopBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    isLoading: Boolean,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit,
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            SpaceTopBar(
                text = stringResource(R.string.news_topbar_title),
                actions = {
                    IconButton(
                        onClick = onSearchTriggered,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search_icon),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
            )
        }

        SearchWidgetState.OPENED -> {
            SearchAppBar(
                modifier = Modifier.statusBarsPadding(),
                textQuery = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked,
                isLoading = isLoading,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleSmallPreview() {
    SpacehubTheme {
        ArticleSmall(
            articleInfo = "SpaceNews • 2 days ago",
            title = "Roscosmos to launch uncrewed Soyuz to replace damaged spacecraft at ISS",
            articleImageUrl = "",
            articleUrl = "",
            onArticleClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleBigPreview() {
    SpacehubTheme {
        ArticleBig(
            articleInfo = "SpaceNews • 2 days ago",
            title = "Roscosmos to launch uncrewed Soyuz to replace damaged spacecraft at ISS",
            articleImageUrl = "",
            articleUrl = "",
            onArticleClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsScreenPreview() {
    SpacehubTheme {
        NewsScreen(
            modifier = Modifier.fillMaxSize(),
            uiState = NewsUiState(articles = Article.Mocks.articlesMocks),
            onRefresh = {},
            onArticleClick = {},
            onTryAgainClicked = {},
        )
    }
}
