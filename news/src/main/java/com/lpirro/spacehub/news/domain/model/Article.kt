/*
 *
 *  * SpaceHub - Designed and Developed by LPirro (Leonardo Pirro)
 *  * Copyright (C) 2023 Leonardo Pirro
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.lpirro.spacehub.news.domain.model

data class Article(
    val id: Int,
    val featured: Boolean,
    val publishedAt: String?,
    val imageUrl: String,
    val newsSite: String,
    val title: String,
    val url: String,
    val launches: List<RelatedLaunch>,
    val updatedAt: String,
    val publishDateOffset: String?,
) {

    object Mocks {
        val articlesMocks = listOf(
            Article(
                id = 1,
                featured = true,
                publishedAt = "2024-09-30",
                imageUrl = "https://example.com/image1.jpg",
                newsSite = "Tech News",
                title = "Kotlin is taking over the world",
                url = "https://example.com/article1",
                launches = emptyList(),
                updatedAt = "2024-09-30",
                publishDateOffset = "5h ago",
            ),
            Article(
                id = 2,
                featured = false,
                publishedAt = "2024-09-29",
                imageUrl = "https://example.com/image2.jpg",
                newsSite = "Android Weekly",
                title = "Top 10 Android Development Trends",
                url = "https://example.com/article2",
                launches = emptyList(),
                updatedAt = "2024-09-29",
                publishDateOffset = "1d ago",
            ),
            Article(
                id = 3,
                featured = true,
                publishedAt = "2024-09-28",
                imageUrl = "https://example.com/image3.jpg",
                newsSite = "Mobile Dev Today",
                title = "Why Jetpack Compose is the Future of UI Development",
                url = "https://example.com/article3",
                launches = emptyList(),
                updatedAt = "2024-09-28",
                publishDateOffset = "2d ago",
            ),
            Article(
                id = 4,
                featured = false,
                publishedAt = "2024-09-27",
                imageUrl = "https://example.com/image4.jpg",
                newsSite = "Kotlin Digest",
                title = "Kotlin Multiplatform: A Deep Dive",
                url = "https://example.com/article4",
                launches = emptyList(),
                updatedAt = "2024-09-27",
                publishDateOffset = "3d ago",
            ),
            Article(
                id = 5,
                featured = true,
                publishedAt = "2024-09-26",
                imageUrl = "https://example.com/image5.jpg",
                newsSite = "Dev Weekly",
                title = "Migrating from Java to Kotlin: Best Practices",
                url = "https://example.com/article5",
                launches = emptyList(),
                updatedAt = "2024-09-26",
                publishDateOffset = "4d ago",
            ),
            Article(
                id = 6,
                featured = false,
                publishedAt = "2024-09-25",
                imageUrl = "https://example.com/image6.jpg",
                newsSite = "Mobile Tech Daily",
                title = "Understanding Coroutines in Kotlin",
                url = "https://example.com/article6",
                launches = emptyList(),
                updatedAt = "2024-09-25",
                publishDateOffset = "5d ago",
            ),
        )
    }
}
