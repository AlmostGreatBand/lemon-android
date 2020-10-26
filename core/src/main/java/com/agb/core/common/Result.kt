package com.agb.core.common

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

val Result<*>.isSuccessful
    get() = this is Result.Success && data != null
