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

package com.spacehub.core.common.util

import android.graphics.Bitmap
import android.graphics.Color
import coil.size.Size
import coil.transform.Transformation

class RemovePaddingTransformation : Transformation {

    override val cacheKey: String = "RemovePaddingTransformation"
    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        var startX = 0
        loop@ for (x in 0 until input.width) {
            for (y in 0 until input.height) {
                if (input.getPixel(x, y) != Color.TRANSPARENT) {
                    startX = x
                    break@loop
                }
            }
        }
        var startY = 0
        loop@ for (y in 0 until input.height) {
            for (x in 0 until input.width) {
                if (input.getPixel(x, y) != Color.TRANSPARENT) {
                    startY = y
                    break@loop
                }
            }
        }
        var endX = input.width - 1
        loop@ for (x in endX downTo 0) {
            for (y in 0 until input.height) {
                if (input.getPixel(x, y) != Color.TRANSPARENT) {
                    endX = x
                    break@loop
                }
            }
        }
        var endY = input.height - 1
        loop@ for (y in endY downTo 0) {
            for (x in 0 until input.width) {
                if (input.getPixel(x, y) != Color.TRANSPARENT) {
                    endY = y
                    break@loop
                }
            }
        }

        val newWidth = endX - startX + 1
        val newHeight = endY - startY + 1

        return Bitmap.createBitmap(input, startX, startY, newWidth, newHeight)
    }
}
