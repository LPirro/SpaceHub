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
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.lpirro.core.databinding.ViewLaunchCountdownBinding
import com.robinhood.ticker.TickerUtils
import java.text.DecimalFormat

class LaunchCountdownView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding =
        ViewLaunchCountdownBinding.inflate(LayoutInflater.from(context), this, true)

    private var countDownTimer: CountDownTimer? = null
    private var netMillis: Long? = null

    init {
        binding.days.setCharacterLists(TickerUtils.provideNumberList())
        binding.hours.setCharacterLists(TickerUtils.provideNumberList())
        binding.minutes.setCharacterLists(TickerUtils.provideNumberList())
        binding.seconds.setCharacterLists(TickerUtils.provideNumberList())
    }

    fun startCountdown(netMillis: Long) {
        this.netMillis = netMillis
        countDownTimer = object : CountDownTimer(netMillis - System.currentTimeMillis(), 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished) / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                val days = hours / 24

                binding.days.text = days.asTwoDecimalString()
                binding.hours.text = (hours % 24).asTwoDecimalString()
                binding.minutes.text = (minutes % 60).asTwoDecimalString()
                binding.seconds.text = (seconds % 60).asTwoDecimalString()
            }

            override fun onFinish() {
                this.cancel()
            }
        }

        countDownTimer?.start()
    }

    fun Long.asTwoDecimalString(): String {
        val format = DecimalFormat("00")
        return format.format(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        countDownTimer?.cancel()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        netMillis?.let { startCountdown(it) }
    }
}
