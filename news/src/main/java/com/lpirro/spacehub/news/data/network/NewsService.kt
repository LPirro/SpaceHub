package com.lpirro.spacehub.news.data.network

import com.lpirro.spacehub.core.model.PaginatedResultRemote
import com.lpirro.spacehub.news.data.network.model.ArticleRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    // TODO: Implement pagination as part of https://linear.app/spacehub/issue/SH-78/news-screen-pagination
    @GET("articles")
    suspend fun getArticles(@Query("limit") resultLimit: String = "50"): PaginatedResultRemote<List<ArticleRemote>>

    // TODO: Implement pagination as part of https://linear.app/spacehub/issue/SH-78/news-screen-pagination
    @GET("articles")
    suspend fun filterArticles(
        @Query("title_contains") title: String,
        @Query("limit") resultLimit: String = "50",
    ): PaginatedResultRemote<List<ArticleRemote>>
}
