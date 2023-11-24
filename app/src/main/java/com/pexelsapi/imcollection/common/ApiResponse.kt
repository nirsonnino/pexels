package com.pexelsapi.imcollection.common

sealed class ApiResponse<out T> {
    data class Success<out T>(
        val data: T?
    ): ApiResponse<T>()

    data class Failure(
        val e: Exception?
    ): ApiResponse<Nothing>()

    data object Idle : ApiResponse<Nothing>()
    data object Loading: ApiResponse<Nothing>()
}