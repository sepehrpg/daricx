package com.example.common.result

/**
 * Wraps an AppError so Paging UI can access it via LoadState.Error.throwable.
 */
class PagingException(val appError: AppError) : Exception(appError.message, appError.cause)
