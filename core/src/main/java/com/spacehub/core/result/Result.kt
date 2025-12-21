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
package com.spacehub.core.result

/**
 * A generic wrapper for operation results that can either succeed or fail.
 * This makes error handling explicit in the type system.
 */
sealed class Result<out T> {
    /**
     * Represents a successful result with data.
     */
    data class Success<T>(val data: T) : Result<T>()

    /**
     * Represents a failed result with a structured error.
     */
    data class Error(val error: DataError) : Result<Nothing>()
}

/**
 * Extension to map Result<T> to Result<R>
 */
inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error -> Result.Error(error)
}

/**
 * Extension to handle success case
 */
inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Result.Success) action(data)
    return this
}

/**
 * Extension to handle error case
 */
inline fun <T> Result<T>.onError(action: (DataError) -> Unit): Result<T> {
    if (this is Result.Error) action(error)
    return this
}

/**
 * Get data or null
 */
fun <T> Result<T>.getOrNull(): T? = when (this) {
    is Result.Success -> data
    is Result.Error -> null
}

/**
 * Get data or default value
 */
fun <T> Result<T>.getOrDefault(default: T): T = when (this) {
    is Result.Success -> data
    is Result.Error -> default
}

/**
 * Get data or throw exception
 */
fun <T> Result<T>.getOrThrow(): T = when (this) {
    is Result.Success -> data
    is Result.Error -> throw error.toException()
}
