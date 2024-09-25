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

package com.lpirro.spacehub.news.data.network.model

import com.google.gson.annotations.SerializedName

data class ArticleRemote(
    @SerializedName("id") val id: Int,
    @SerializedName("featured") val featured: Boolean,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("news_site") val newsSite: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("launches") val launches: List<RelatedLaunchRemote>,
    @SerializedName("updated_at") val updatedAt: String,
)
