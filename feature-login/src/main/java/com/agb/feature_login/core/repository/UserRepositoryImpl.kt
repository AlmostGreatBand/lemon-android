package com.agb.feature_login.core.repository

import com.agb.core.common.Operation
import com.agb.core.common.Result
import com.agb.core.common.exceptions.LogicError
import com.agb.core.di.Local
import com.agb.core.di.Remote
import com.agb.core.domain.model.User
import com.agb.feature_login.core.datasource.AuthDataSource
import com.agb.feature_login.core.datasource.UserDataSource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @Local private val local: UserDataSource,
    @Remote private val remote: UserDataSource,
    private val authenticator: AuthDataSource,
) : UserRepository {
    private var user: User? = null
    override val currentUser: User? get() = user

    override suspend fun saveUserInfo(user: User): Operation {
        if (this.user == null) {
            return Result.Error(LogicError.UserNotExists)
        }

        return remote.saveUserInfo(user).onSuccess { cache(user) }
    }

    override suspend fun login(login: String, password: String): Operation {
        return authenticator.loginUser(login, password).map { cache(it) }
    }

    override fun clear() {
        user = null
    }

    private suspend fun cache(user: User) {
        this.user = user
        local.saveUserInfo(user)
    }
}
