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

package com.lpirro.launch_detail.overview.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.lpirro.core.extensions.show
import com.lpirro.launch_detail.databinding.ItemWatchliveBinding
import com.lpirro.launch_detail.overview.model.LaunchOverviewItem
import com.lpirro.launch_detail.overview.model.WatchLiveUi
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

fun watchLiveDelegate(fullScreenClickListener: (String) -> Unit) =
    adapterDelegateViewBinding<WatchLiveUi, LaunchOverviewItem, ItemWatchliveBinding>(
        { layoutInflater, root -> ItemWatchliveBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    item.youtubeVideoId.let { youTubePlayer.cueVideo(it, 0f) }
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState
                ) {
                    if (state == PlayerConstants.PlayerState.PLAYING) {
                        binding.watchFullScreenButton.show()
                    }
                }
            })

            binding.watchFullScreenButton.setOnClickListener {
                fullScreenClickListener.invoke(item.youtubeVideoId)
            }
        }
    }
