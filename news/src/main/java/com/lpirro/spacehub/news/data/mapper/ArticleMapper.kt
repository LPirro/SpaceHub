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

package com.lpirro.spacehub.news.data.mapper

import com.lpirro.spacehub.core.util.DateParser
import com.lpirro.spacehub.news.data.network.model.ArticleRemote
import com.lpirro.spacehub.news.domain.model.Article
import com.lpirro.spacehub.news.domain.model.RelatedLaunch

interface ArticleMapper {
    fun mapToDomain(articleRemote: ArticleRemote): Article
}

class ArticleMapperImpl(
    private val dateParser: DateParser,
) : ArticleMapper {
    override fun mapToDomain(articleRemote: ArticleRemote) = Article(
        id = articleRemote.id,
        featured = articleRemote.featured,
        publishedAt = articleRemote.publishedAt,
        publishDateOffset = dateParser.formatToTimeAgo(articleRemote.publishedAt),
        imageUrl = articleRemote.imageUrl,
        newsSite = articleRemote.newsSite,
        title = articleRemote.title,
        url = articleRemote.url,
        launches = articleRemote.launches.map { RelatedLaunch((it.launchId)) },
        updatedAt = articleRemote.updatedAt,
    )
}
