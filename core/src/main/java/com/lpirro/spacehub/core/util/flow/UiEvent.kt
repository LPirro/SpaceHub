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

@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lpirro.spacehub.core.util.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.AbstractFlow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

class UiEvent<T> : AbstractFlow<T>() {

    private val events = Channel<T>(capacity = 32, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    suspend fun emit(event: T) {
        withContext(Dispatchers.Main.immediate) {
            events.send(event)
        }
    }

    override suspend fun collectSafely(collector: FlowCollector<T>) {
        events.receiveAsFlow().collect {
            collector.emit(it)
        }
    }
}
