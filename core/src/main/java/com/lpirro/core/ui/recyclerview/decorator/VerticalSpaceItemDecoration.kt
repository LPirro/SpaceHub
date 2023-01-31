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

package com.lpirro.core.ui.recyclerview.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * This class adds Vertical spacing on the RecyclerView
 *
 * @property spaceSize the spacing in dp between the elements
 * @property edgeSpacing the spacing in dp for the first element and last element of the RecyclerView
 */

class VerticalSpaceItemDecoration(
    private val spaceSize: Int,
    private val edgeSpacing: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val currentPosition = parent.getChildAdapterPosition(view)
        val isFirstPosition = currentPosition == 0
        val isLastPosition = state.itemCount > 0 && currentPosition == state.itemCount - 1
        val isOnlyOneItem = state.itemCount == 1

        with(outRect) {
            when {
                isOnlyOneItem -> {
                    top = edgeSpacing
                    bottom = edgeSpacing
                }
                isFirstPosition -> {
                    top = edgeSpacing
                    bottom = spaceSize / 2
                }
                isLastPosition -> {
                    top = spaceSize / 2
                    bottom = edgeSpacing
                }
                else -> {
                    top = spaceSize / 2
                    bottom = spaceSize / 2
                }
            }
        }
    }
}
