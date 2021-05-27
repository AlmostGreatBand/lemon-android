package com.agb.data.remote

import com.agb.core.common.Result
import com.agb.core.datasource.UserDataSource
import com.agb.core.domain.model.User
import com.agb.data.remote.api.UserApi

class UserRemoteDataSource(private val userApi: UserApi) : UserDataSource {
    override suspend fun getUserInfo(): Result<User> {
        return Result.Success(User("", "", ""))
    }

    override suspend fun saveUserInfo(user: User): Result<Unit> {
        return Result.Ok
    }
}
