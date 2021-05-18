package com.agb.feature_login.data.remote

import com.agb.core.common.Result
import com.agb.core.common.exceptions.HttpLemonException
import com.agb.core.common.exceptions.LogicError
import com.agb.core.common.exceptions.LogicException
import com.agb.core.common.exceptions.UnexpectedLemonException
import com.agb.core.domain.model.User
import com.agb.feature_login.core.datasource.AuthDataSource
import com.agb.feature_login.data.remote.api.UserApi
import javax.inject.Inject
import okhttp3.Credentials
import retrofit2.HttpException

class AuthRemoteDataSource @Inject constructor(
    private val api: UserApi
) : AuthDataSource {
    override suspend fun loginUser(login: String, password: String): Result<User> {
        return try {
            val resp = api.login(Credentials.basic(login, password))
            Result.Success(resp)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> Result.Error(handleHttpException(e))
                else -> Result.Error(UnexpectedLemonException(e))
            }
        }
    }

    private fun handleHttpException(exception: HttpException) = when (exception.code()) {
        403 -> LogicException(LogicError.AccessDenied)
        else -> HttpLemonException(exception.code(), exception.message())
    }
}
