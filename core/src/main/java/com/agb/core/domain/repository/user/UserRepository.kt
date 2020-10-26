package com.agb.core.domain.repository.user

import com.agb.core.domain.model.User

interface UserRepository {
    suspend fun getUserInfo(): User
    suspend fun saveUserInfo(user: User)
}
