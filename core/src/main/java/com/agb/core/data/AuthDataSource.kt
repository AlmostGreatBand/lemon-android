package com.agb.core.data

import com.agb.core.common.Result
import com.agb.core.domain.model.User

interface AuthDataSource {
    suspend fun loginUser(login: String, password: String): Result<User>
}
