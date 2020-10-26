package com.agb.core.data

import com.agb.core.domain.model.User

interface UserDataSource {
    suspend fun getUserInfo(): User
    suspend fun saveUserInfo(user: User)
}
