package com.agb.data.remote

import com.agb.core.common.exceptions.HttpLemonException
import com.agb.core.common.exceptions.LogicError
import com.agb.core.common.exceptions.LogicException
import retrofit2.HttpException

interface HttpExceptionHandler {
    fun handleHttpException(exception: HttpException) = when (exception.code()) {
        403 -> LogicException(LogicError.AccessDenied)
        428 -> LogicException(LogicError.ValidationError)
        else -> HttpLemonException(exception.code(), exception.message())
    }
}
