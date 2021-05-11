package com.agb.feature_login.data.local

import android.content.Context
import com.agb.core.common.Result
import com.agb.core.domain.model.User
import com.agb.feature_login.core.datasource.UserDataSource
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val context: Context
) : UserDataSource {
    override suspend fun getUserInfo(): Result<User> {
        return Result.Success(User(context.packageName, "", ""))
    }

    override suspend fun saveUserInfo(user: User): Result<Unit> {
        return Result.Ok
    }
}
