package com.example.network.errors

import com.example.common.result.AppError
import com.example.common.result.PagingException
import io.ktor.client.plugins.*          // ResponseException, ClientRequestException, ...
import io.ktor.http.*                   // HttpStatusCode
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

fun Throwable.toAppError(): AppError = when (this) {
    // Covers network I/O errors (timeout, no internet, etc.)
    is IOException -> AppError.Network(this)

    is ResponseException -> {
        val statusCode = response.status.value
        val statusMessage = response.status.description
        AppError.Http(
            code = statusCode,
            message = message ?: "HTTP $statusCode $statusMessage",
            cause = this
        )
    }

    // Ktor HTTP errors (status code not in 2xx)
    /*is ResponseException -> {
        val statusCode = response.status.value
        val statusMessage = response.status.description

        when (response.status) {
            HttpStatusCode.BadRequest -> AppError.Http(400, "Bad request", this)
            HttpStatusCode.Unauthorized -> AppError.Http(401, "Unauthorized", this)
            HttpStatusCode.NotFound -> AppError.Http(404, "Not found", this)
            HttpStatusCode.InternalServerError -> AppError.Http(500, "Server error", this)
            else -> AppError.Http(
                code = statusCode,
                message = message ?: "HTTP $statusCode $statusMessage",
                cause = this
            )
        }
    }*/

    // JSON / Serialization errors
    is SerializationException -> AppError.Serialization(this)

    // Anything else
    else -> AppError.Unknown(this)
}

/**
 * Map any Throwable to PagingException using network-level toAppError().
 */
fun Throwable.toPagingException(): PagingException = PagingException(this.toAppError())
