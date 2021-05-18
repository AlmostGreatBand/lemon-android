package com.agb.feature_login.data.remote

import com.agb.core.common.Result
import com.agb.core.domain.model.User
import com.agb.feature_login.core.datasource.UserDataSource
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor() : UserDataSource {
    override suspend fun getUserInfo(): Result<User> {
        return Result.Success(User("", "", ""))
    }

    override suspend fun saveUserInfo(user: User): Result<Unit> {
        return Result.Ok
    }
}
