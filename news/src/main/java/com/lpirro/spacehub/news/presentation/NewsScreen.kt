@file:OptIn(ExperimentalMaterial3Api::class)

package com.lpirro.spacehub.news.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lpirro.spacehub.core.composables.SpaceTopBar
import com.lpirro.spacehub.core.ui.composables.ErrorScreen
import com.lpirro.spacehub.core.ui.theme.SpacehubTheme
import com.lpirro.spacehub.news.R
import com.lpirro.spacehub.news.domain.model.Article

@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel(), onArticleClick: (url: String) -> Unit) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { SpaceTopBar(text = stringResource(R.string.news_topbar_title)) },
    ) { innerPadding ->
        NewsScreen(
            modifier = Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding()),
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
                ArticleSmall(
                    newsSite = article.newsSite,
                    publishedAt = article.publishedAt ?: "",
                    title = article.title,
                    articleImageUrl = article.imageUrl,
                    articleUrl = article.url,
                    onArticleClick = onArticleClick,
                )
            }
        }
    }
}

@Composable
private fun ArticleSmall(
    newsSite: String,
    publishedAt: String,
    title: String,
    articleImageUrl: String,
    articleUrl: String,
    onArticleClick: (url: String) -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable { onArticleClick.invoke(articleUrl) }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(10.dp),
                    painter = painterResource(com.lpirro.spacehub.core.R.drawable.newspaper_variant_outline),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null,
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = newsSite,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
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

@Preview(showBackground = true)
@Composable
private fun ArticleSmallPreview() {
    SpacehubTheme {
        ArticleSmall(
            newsSite = "SpaceNews",
            publishedAt = "2 days ago",
            title = "Roscosmos to launch uncrewed Soyuz to replace damaged spacecraft at ISS",
            articleImageUrl = "",
            articleUrl = "",
            onArticleClick = {},
        )
    }
}
