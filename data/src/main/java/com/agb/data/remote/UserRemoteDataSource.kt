package com.agb.data.remote

import com.agb.core.common.Operation
import com.agb.core.common.Result
import com.agb.core.common.exceptions.HttpLemonException
import com.agb.core.common.exceptions.LogicError
import com.agb.core.common.exceptions.LogicException
import com.agb.core.common.exceptions.UnexpectedLemonException
import com.agb.core.datasource.UserDataSource
import com.agb.core.domain.model.User
import com.agb.data.remote.api.UserApi
import retrofit2.HttpException

class UserRemoteDataSource(private val userApi: UserApi) : UserDataSource {
    override suspend fun getUserInfo(): Result<User> {
        return try {
            val resp = userApi.profile()
            Result.Success(resp)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> Result.Error(handleHttpException(e))
                else -> Result.Error(UnexpectedLemonException(e))
            }
        }
    }

    override suspend fun saveUserInfo(user: User): Result<Unit> {
        return try {
            Result.Success(userApi.updateProfile(user))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> Result.Error(handleHttpException(e))
                else -> Result.Error(UnexpectedLemonException(e))
            }
        }
    }

    override suspend fun deleteUserInfo(): Operation = error("Not allowed!")

    private fun handleHttpException(exception: HttpException) = when (exception.code()) {
        403 -> LogicException(LogicError.AccessDenied)
        428 -> LogicException(LogicError.ValidationError)
        else -> HttpLemonException(exception.code(), exception.message())
    }
}
