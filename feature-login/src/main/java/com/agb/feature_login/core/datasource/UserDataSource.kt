package com.agb.feature_login.core.datasource

import com.agb.core.common.Result
import com.agb.core.domain.model.User

interface UserDataSource {
    suspend fun getUserInfo(): Result<User>
    suspend fun saveUserInfo(user: User): Result<Unit>
}
