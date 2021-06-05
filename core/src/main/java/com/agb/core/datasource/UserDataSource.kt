package com.agb.core.datasource

import com.agb.core.common.Operation
import com.agb.core.common.Result
import com.agb.core.domain.model.User

interface UserDataSource {
    suspend fun getUserInfo(): Result<User>
    suspend fun saveUserInfo(user: User): Operation
    suspend fun deleteUserInfo(): Operation
}
