package com.agb.core.domain.repository

import com.agb.core.common.Operation
import com.agb.core.common.Result
import com.agb.core.domain.model.User

interface UserRepository {
    suspend fun getUserInfo(): Result<User>
    suspend fun saveUserInfo(user: User): Operation
    suspend fun login(login: String, password: String): Operation
    suspend fun logout(): Operation
}
