package com.agb.core.domain.repository.user

import com.agb.core.common.Operation
import com.agb.core.domain.model.User

interface UserRepository {
    val currentUser: User?

    suspend fun saveUserInfo(user: User): Operation
    suspend fun login(login: String, password: String): Operation

    fun clear()
}
