package com.lpirro.spacehub.news.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lpirro.spacehub.core.composables.SpaceTopBar
import com.lpirro.spacehub.news.R

@Composable
fun NewsScreen() {
    Scaffold(
        topBar = { SpaceTopBar(text = stringResource(R.string.news_topbar_title)) },
    ) { innerPadding ->
        Text(modifier = Modifier.padding(innerPadding), text = "News")
    }
}
