package com.agb.lemon_android.frameworks.datasource.local

import com.agb.core.common.Result
import com.agb.core.data.UserDataSource
import com.agb.core.domain.model.User

class UserLocalDataSource(private val prefManager: PreferencesManager) : UserDataSource {
    override suspend fun getUserInfo(): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun saveUserInfo(user: User): Result<Unit> {
        TODO("Not yet implemented")
    }
}
