/*
 * Spacehub - A companion app for exploring space launches, news, and missions
 * Copyright (C) 2024 Luca Pirro
 */

package com.lpirro.spacehub.core.result

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
