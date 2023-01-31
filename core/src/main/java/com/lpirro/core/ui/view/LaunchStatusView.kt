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

package com.lpirro.core.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.lpirro.core.R
import com.lpirro.core.databinding.ViewLaunchStatusBinding
import com.lpirro.core.extensions.getColorFromAttr
import com.lpirro.core.extensions.visible
import com.lpirro.domain.models.Status

class LaunchStatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val binding: ViewLaunchStatusBinding

    init {
        binding = ViewLaunchStatusBinding.inflate(LayoutInflater.from(context), this, true)
        background = ContextCompat.getDrawable(context, R.drawable.status_bg)
        binding.status.includeFontPadding = false
        binding.status.setTextAppearance(com.google.android.material.R.style.TextAppearance_Material3_LabelLarge)
    }

    @DrawableRes
    var icon: Int? = null
        set(value) {
            field = value
            binding.icon.visible = value != null
            value?.let {
                binding.icon.setImageResource(it)
            }
        }

    fun setStatus(status: Status, text: String = status.abbrev) {
        val textColor: Int
        val backgroundColor: Int

        when (status) {
            is Status.Failure -> {
                textColor =
                    context.getColorFromAttr(com.google.android.material.R.attr.colorOnErrorContainer)
                backgroundColor =
                    context.getColorFromAttr(com.google.android.material.R.attr.colorErrorContainer)
            }
            is Status.Go, is Status.Success -> {
                textColor = context.getColorFromAttr(R.attr.colorOnSuccessContainer)
                backgroundColor = context.getColorFromAttr(R.attr.colorSuccessContainer)
            }
            is Status.InFlight -> {
                textColor =
                    context.getColorFromAttr(com.google.android.material.R.attr.colorOnPrimary)
                backgroundColor =
                    context.getColorFromAttr(com.google.android.material.R.attr.colorPrimary)
            }
            is Status.TBC, is Status.TBD -> {
                textColor = context.getColorFromAttr(R.attr.colorOnWarningContainer)
                backgroundColor = context.getColorFromAttr(R.attr.colorWarningContainer)
            }
            else -> {
                textColor = context.getColorFromAttr(R.attr.colorOnWarningContainer)
                backgroundColor = context.getColorFromAttr(R.attr.colorWarningContainer)
            }
        }

        this.backgroundTintList = ColorStateList.valueOf(backgroundColor)
        icon?.let { binding.icon.imageTintList = ColorStateList.valueOf(textColor) }
        binding.status.setTextColor(textColor)
        binding.status.text = text
    }
}
