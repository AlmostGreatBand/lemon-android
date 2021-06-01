package com.agb.data.remote

import com.agb.core.common.Operation
import com.agb.core.common.Result
import com.agb.core.common.exceptions.HttpLemonException
import com.agb.core.common.exceptions.LogicError
import com.agb.core.common.exceptions.UnexpectedLemonException
import com.agb.core.datasource.RegistrationDataSource
import com.agb.core.domain.model.User
import com.agb.data.remote.api.RegistrationApi
import retrofit2.HttpException

class RegistrationRemoteDataSource(
    private val api: RegistrationApi,
) : RegistrationDataSource {
    override suspend fun register(user: User): Operation {
        return try {
            api.register(user)
            Result.Ok
        } catch (e: Exception) {
            when (e) {
                is HttpException -> handleHttpException(e)
                else -> Result.Error(UnexpectedLemonException(e))
            }
        }
    }

    private fun handleHttpException(e: HttpException): Result.Error = when (e.code()) {
        409 -> Result.Error(LogicError.UserExists)
        428 -> Result.Error(LogicError.ValidationError)
        else -> Result.Error(HttpLemonException(e.code(), e.message()))
    }
}
