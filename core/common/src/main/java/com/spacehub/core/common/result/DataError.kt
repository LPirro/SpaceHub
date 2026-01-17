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

package com.spacehub.core.common.result

/**
 * Represents different types of errors that can occur during data operations.
 * This provides structured error handling instead of relying on raw exceptions.
 */
sealed class DataError {
    /**
     * Network-related error with optional HTTP status code
     */
    data class Network(val code: Int? = null, val message: String) : DataError()

    /**
     * Data parsing/serialization error
     */
    data class Parse(val message: String) : DataError()

    /**
     * No internet connection available
     */
    data object NoInternet : DataError()

    /**
     * Authentication/authorization error (401)
     */
    data object Unauthorized : DataError()

    /**
     * Resource not found (404)
     */
    data object NotFound : DataError()

    /**
     * Server error (5xx)
     */
    data object ServerError : DataError()

    /**
     * Unknown/unexpected error
     */
    data object Unknown : DataError()
}

/**
 * Convert DataError to a user-friendly message
 */
fun DataError.toUserMessage(): String = when (this) {
    is DataError.Network -> "Network error: $message"
    is DataError.NoInternet -> "No internet connection. Please check your network."
    is DataError.Parse -> "Unable to load data. Please try again."
    is DataError.Unauthorized -> "Session expired. Please login again."
    is DataError.NotFound -> "Data not found."
    is DataError.ServerError -> "Server error. Please try again later."
    is DataError.Unknown -> "Something went wrong. Please try again."
}

/**
 * Convert DataError to an Exception (for cases where exception is needed)
 */
fun DataError.toException(): Exception = when (this) {
    is DataError.Network -> Exception("Network error [$code]: $message")
    is DataError.NoInternet -> Exception("No internet connection")
    is DataError.Parse -> Exception("Parse error: $message")
    is DataError.Unauthorized -> Exception("Unauthorized")
    is DataError.NotFound -> Exception("Not found")
    is DataError.ServerError -> Exception("Server error")
    is DataError.Unknown -> Exception("Unknown error")
}
