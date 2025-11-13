package com.example.common.result



sealed class AppError(
    val message: String? = null,
    val cause: Throwable? = null
) {
    class Network(cause: Throwable? = null) : AppError("Network error", cause)
    class Http(val code: Int, message: String? = null, cause: Throwable? = null) :
        AppError(message ?: "HTTP $code", cause)
    class Serialization(cause: Throwable? = null) : AppError("Serialization error", cause)
    class Unknown(cause: Throwable? = null) : AppError("Unknown error", cause)

}


