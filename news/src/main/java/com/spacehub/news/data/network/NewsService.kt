package com.spacehub.news.data.network

import com.spacehub.news.data.network.model.ArticleRemote
import com.spacehub.common.models.remote.PaginatedResultRemote
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
