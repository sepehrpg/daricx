package com.example.common.result

sealed class AppResult<out T> {
    data object Loading : AppResult<Nothing>()
    data class Success<T>(val data: T) : AppResult<T>()
    data class Error(val error: AppError) : AppResult<Nothing>()
}