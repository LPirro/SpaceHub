/*
 *
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
 *
 */

package com.lpirro.news.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.lpirro.core.extensions.visible
import com.lpirro.domain.models.Article
import com.lpirro.news.R
import com.lpirro.news.databinding.ItemFeaturedBinding
import com.lpirro.news.databinding.ItemNewsBinding

const val TYPE_ARTICLE = 0
const val TYPE_FEATURED_ARTICLE = 1

class ArticleAdapter(
    private val articleClick: (articleUrl: String) -> Unit,
    private val relatedLaunchClick: (launchId: String) -> Unit,
) : ListAdapter<Article, ViewHolder>(ArticleDiffCallback) {

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].featured) {
            true -> TYPE_FEATURED_ARTICLE
            else -> TYPE_ARTICLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = when (viewType) {
            TYPE_ARTICLE -> ArticleViewHolder(
                ItemNewsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> FeaturedArticleViewHolder(
                ItemFeaturedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)

        when (holder) {
            is ArticleViewHolder -> {
                holder.binding.articleTitle.text = article.title
                holder.binding.articleInfo.text = "${article.newsSite} • ${article.publishDateOffset}"
                holder.binding.relatedLaunchButton.visible = article.launches.isNotEmpty()
                holder.itemView.setOnClickListener {
                    articleClick.invoke(article.url)
                }

                holder.binding.relatedLaunchButton.setOnClickListener {
                    relatedLaunchClick.invoke(article.launches.first().launchId)
                }

                val roundCornerSize =
                    holder.itemView.context.resources.getDimensionPixelSize(R.dimen.article_image_corners)

                val requestOptions =
                    RequestOptions().transform(CenterCrop(), RoundedCorners(roundCornerSize))
                Glide.with(holder.itemView.context)
                    .load(article.imageUrl)
                    .placeholder(R.drawable.article_image_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(requestOptions)
                    .into(holder.binding.articleImage)
            }
            is FeaturedArticleViewHolder -> {
                holder.binding.articleTitle.text = article.title
                holder.binding.articleInfo.text = "${article.newsSite} • ${article.publishDateOffset}"
                holder.binding.relatedLaunchButton.visible = article.launches.isNotEmpty()

                holder.itemView.setOnClickListener {
                    articleClick.invoke(article.url)
                }

                holder.binding.relatedLaunchButton.setOnClickListener {
                    relatedLaunchClick.invoke(article.launches.first().launchId)
                }

                val roundCornerSize =
                    holder.itemView.context.resources.getDimensionPixelSize(R.dimen.article_image_corners)

                val requestOptions =
                    RequestOptions().transform(CenterCrop(), RoundedCorners(roundCornerSize))
                Glide.with(holder.itemView.context)
                    .load(article.imageUrl)
                    .placeholder(R.drawable.article_image_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(requestOptions)
                    .into(holder.binding.articleImage)
            }
        }
    }

    inner class ArticleViewHolder(val binding: ItemNewsBinding) : ViewHolder(binding.root)
    inner class FeaturedArticleViewHolder(val binding: ItemFeaturedBinding) : ViewHolder(binding.root)
}
