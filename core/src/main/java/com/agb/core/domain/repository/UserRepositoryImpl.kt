package com.agb.core.domain.repository

import com.agb.core.common.Operation
import com.agb.core.common.Result
import com.agb.core.datasource.AuthDataSource
import com.agb.core.datasource.RegistrationDataSource
import com.agb.core.datasource.UserDataSource
import com.agb.core.domain.model.User

class UserRepositoryImpl(
    private val local: UserDataSource,
    private val remote: UserDataSource,
    private val authenticator: AuthDataSource,
    private val registration: RegistrationDataSource
) : UserRepository {
    override suspend fun getUserInfo(): Result<User> = local.getUserInfo()

    override suspend fun saveUserInfo(user: User): Operation = remote.saveUserInfo(user)
        .onSuccess { cache(user) }

    override suspend fun register(user: User): Operation = registration.register(user)
        .onSuccess { cache(user) }

    override suspend fun login(login: String, password: String): Operation {
        return authenticator.loginUser(login, password).map { cache(it) }
    }

    override suspend fun logout(): Operation = local.deleteUserInfo()

    private suspend fun cache(user: User) {
        local.saveUserInfo(user)
    }
}
