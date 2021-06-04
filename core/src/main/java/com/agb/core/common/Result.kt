package com.agb.core.common

import com.agb.core.common.exceptions.LemonException
import com.agb.core.common.exceptions.LogicError
import com.agb.core.common.exceptions.LogicLemonException
import com.agb.core.common.exceptions.UnexpectedLemonException

sealed class Result<out R> {
    /** [Result] that finished successfully
     * @param data value of successful operation */
    data class Success<out T>(val data: T) : Result<T>()

    /** [Result] that is still in progress */
    object Pending : Result<Nothing>()

    /** [Result] that failed to complete successfully.
     * @param exception [LemonException] with which Result was failed
     */
    data class Error(val exception: LemonException) : Result<Nothing>() {
        /** Produces [Error] with [UnexpectedLemonException]
         * @param message message of [UnexpectedLemonException] */
        constructor(message: String) : this(UnexpectedLemonException(message))

        /** Produces [Error] with [LogicLemonException]
         * @param logicError error of [LogicLemonException] */
        constructor(logicError: LogicError) : this(LogicLemonException(logicError))
    }

    /** If this [Result] is [Error], unwraps error and passes is as parameter to [cb].
     * @return this [Result] */
    inline fun onError(cb: (LemonException) -> Unit): Result<R> = apply {
        if (this is Error) cb(exception)
    }

    /** If this [Result] is [Success], unwraps data and passes is as parameter to [cb].
     * @return this [Result] */
    inline fun onSuccess(cb: (R) -> Unit) = apply {
        if (this is Success) cb(data)
    }

    /** If this [Result] is [Pending], invokes [cb].
     * @return this [Result] */
    inline fun onPending(cb: () -> Unit) = apply {
        if (this is Pending) cb()
    }

    /** Applies one of the following operations depending on result state.
     * @param success called with Result data if this result is [Success]
     * @param pending called if this Result is [Pending]
     * @param failure called with Result exception if this result is [Error]
     * @return this [Result] */
    inline fun fold(
        success: (R) -> Unit,
        pending: () -> Unit,
        failure: (LemonException) -> Unit
    ): Result<R> = apply {
        when (this) {
            is Success -> success(data)
            is Pending -> pending()
            is Error -> failure(exception)
        }
    }

    /** Transforms this [Result] to other [Result] if this is [Success].
     * @param cb callback that receives Result's data and returns value of new Result
     * @return same [Result] object if this is either [Pending] or [Error];
     *
     * Otherwise, [Result]<[T]> with value, returned from [cb].*/
    inline fun <T> map(cb: (R) -> T): Result<T> = when (this) {
        Pending -> Pending
        is Error -> this
        is Success -> Success(cb(data))
    }

    /** Transforms this [Result] to other [Result] if this is [Success].
     * @param cb callback that receives Result's data and returns new [Result]
     * @return same [Result] object if this is either [Pending] or [Error];
     *
     * Otherwise, [Result]<[T]> object that has been returned from [cb]*/
    inline fun <T> then(cb: (R) -> Result<T>): Result<T> = when (this) {
        Pending -> Pending
        is Error -> this
        is Success -> cb(data)
    }

    companion object {
        /** Default [Success] object for stateless operations */
        val Ok = Success(Unit)
    }
}

/** Typealias for stateless operation, for example saving or updating data, where no
 * value is returned */
typealias Operation = Result<Unit>

inline fun <T> Result<T>.orElse(transform: (LemonException) -> Result<T>): Result<T> {
    return when (this) {
        Result.Pending -> Result.Pending
        is Result.Error -> transform(this.exception)
        is Result.Success -> this
    }
}
