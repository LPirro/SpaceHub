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

package com.lpirro.launch_detail.overview.presentation

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lpirro.launch_detail.R

class LaunchOverviewItemDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val currentPosition = parent.getChildAdapterPosition(view)
        val isFirstPosition = currentPosition == 0
        val isLastPosition = state.itemCount > 0 && currentPosition == state.itemCount - 1

        val horizontalSpace =
            parent.context.resources.getDimensionPixelSize(R.dimen.launch_detail_horizontal_spacing)
        val verticalSpace =
            parent.context.resources.getDimensionPixelSize(R.dimen.launch_detail_vertical_spacing)

        with(outRect) {
            when {
                isFirstPosition -> {
                    top = 0
                    bottom = verticalSpace / 2
                    left = 0
                    right = 0
                }
                isLastPosition -> {
                    top = verticalSpace / 2
                    bottom = verticalSpace
                    left = horizontalSpace
                    right = horizontalSpace
                }
                else -> {
                    top = verticalSpace / 2
                    bottom = verticalSpace / 2
                    left = horizontalSpace
                    right = horizontalSpace
                }
            }
        }
    }
}
