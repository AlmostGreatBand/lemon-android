package com.agb.core.domain.repository

import com.agb.core.common.Operation
import com.agb.core.common.Result
import com.agb.core.common.exceptions.LogicError
import com.agb.core.datasource.AuthDataSource
import com.agb.core.datasource.UserDataSource
import com.agb.core.domain.model.User

class UserRepositoryImpl(
    private val local: UserDataSource,
    private val remote: UserDataSource,
    private val authenticator: AuthDataSource,
) : UserRepository {
    override suspend fun getUserInfo(): Result<User> = local.getUserInfo()

    override suspend fun saveUserInfo(user: User): Operation {
        if (getUserInfo() is Result.Error) {
            return Result.Error(LogicError.UserNotExists)
        }

        return remote.saveUserInfo(user).onSuccess { cache(user) }
    }

    override suspend fun login(login: String, password: String): Operation {
        return authenticator.loginUser(login, password).map { cache(it) }
    }

    private suspend fun cache(user: User) {
        local.saveUserInfo(user)
    }
}
