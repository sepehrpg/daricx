package com.example.network.errors

import com.example.common.result.AppError
import com.example.common.result.PagingException
import java.io.IOException
import kotlinx.serialization.SerializationException
import retrofit2.HttpException

fun Throwable.toAppError(): AppError = when (this) {
    is IOException -> AppError.Network(this)

    is HttpException -> AppError.Http(this.code(), this.message(), this)
    /*is HttpException -> {
        when (code()) {
            400 -> AppError.Http(400, "Bad request", this)
            401 -> AppError.Http(401, "Unauthorized", this)
            404 -> AppError.Http(404, "Not found", this)
            500 -> AppError.Http(500, "Server error", this)
            else -> AppError.Http(this.code(), this.message(), this)
        }
    }*/

    is SerializationException -> AppError.Serialization(this)
    else -> AppError.Unknown(this)
}

/**
 * Map any Throwable to PagingException using network-level toAppError().
 */
fun Throwable.toPagingException(): PagingException = PagingException(this.toAppError())