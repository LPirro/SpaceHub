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

package com.lpirro.core.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.lpirro.core.R
import com.lpirro.core.databinding.ViewLaunchStatusBinding
import com.lpirro.core.extensions.getColorFromAttr

class LaunchStatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val binding =
        ViewLaunchStatusBinding.inflate(LayoutInflater.from(context))

    init {
        background = ContextCompat.getDrawable(context, R.drawable.status_bg)
        includeFontPadding = false
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        updateColor(text.toString())
    }

    private fun updateColor(text: String?) {
        val textColor: Int
        val backgroundColor: Int

        with(text!!) {
            when {
                contains("flight", ignoreCase = true) -> {
                    textColor = context.getColorFromAttr(com.google.android.material.R.attr.colorOnPrimary)
                    backgroundColor = context.getColorFromAttr(com.google.android.material.R.attr.colorPrimary)
                }
                contains("go", ignoreCase = true) -> {
                    textColor = context.getColorFromAttr(R.attr.colorOnSuccessContainer)
                    backgroundColor = context.getColorFromAttr(R.attr.colorSuccessContainer)
                }
                contains("success", ignoreCase = true) -> {
                    textColor = context.getColorFromAttr(R.attr.colorOnSuccessContainer)
                    backgroundColor = context.getColorFromAttr(R.attr.colorSuccessContainer)
                }
                contains("failure", ignoreCase = true) -> {
                    textColor = context.getColorFromAttr(com.google.android.material.R.attr.colorOnErrorContainer)
                    backgroundColor = context.getColorFromAttr(com.google.android.material.R.attr.colorErrorContainer)
                }
                else -> {
                    textColor = context.getColorFromAttr(R.attr.colorOnWarningContainer)
                    backgroundColor = context.getColorFromAttr(R.attr.colorWarningContainer)
                }
            }
        }

        this.backgroundTintList = ColorStateList.valueOf(backgroundColor)
        this.setTextColor(textColor)
    }
}
