/*
 * SpaceHub - Designed and Developed by LPirro (Leonardo Pirro)
 * Copyright (C) 2023 Leonardo Pirro
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.lpirro.repository.mapper

import com.lpirro.domain.models.Article
import com.lpirro.domain.models.RelatedLaunch
import com.lpirro.network.models.ArticleRemote
import com.lpirro.persistence.model.ArticleLocal
import com.lpirro.persistence.model.RelatedLaunchLocal

class ArticleMapperImpl(private val dateParser: DateParser) : ArticleMapper {
    override fun mapToDomain(articleLocal: ArticleLocal) = Article(
        id = articleLocal.id,
        featured = articleLocal.featured,
        publishedAt = articleLocal.publishedAt,
        publishDateOffset = dateParser.formatToTimeAgo(articleLocal.publishedAt),
        imageUrl = articleLocal.imageUrl,
        newsSite = articleLocal.newsSite,
        title = articleLocal.title,
        url = articleLocal.url,
        launches = articleLocal.launches.map { RelatedLaunch((it.launchId)) },
        updatedAt = articleLocal.updatedAt
    )

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
        updatedAt = articleRemote.updatedAt
    )

    override fun mapToLocal(articleRemote: ArticleRemote) = ArticleLocal(
        id = articleRemote.id,
        featured = articleRemote.featured,
        publishedAt = articleRemote.publishedAt,
        imageUrl = articleRemote.imageUrl,
        newsSite = articleRemote.newsSite,
        title = articleRemote.title,
        url = articleRemote.url,
        launches = articleRemote.launches.map { RelatedLaunchLocal((it.launchId)) },
        updatedAt = articleRemote.updatedAt
    )
}
