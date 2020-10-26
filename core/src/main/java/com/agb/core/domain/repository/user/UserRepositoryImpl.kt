package com.agb.core.domain.repository.user

import com.agb.core.data.UserDataSource
import com.agb.core.domain.model.User

class UserRepositoryImpl(
    private val local: UserDataSource,
    private val remote: UserDataSource,
) : UserRepository {
    override suspend fun getUserInfo(): User {
        TODO("Not yet implemented")
    }

    override suspend fun saveUserInfo(user: User) {
        TODO("Not yet implemented")
    }
}
