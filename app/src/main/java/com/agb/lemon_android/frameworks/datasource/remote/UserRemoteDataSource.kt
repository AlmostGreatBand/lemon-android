package com.agb.lemon_android.frameworks.datasource.remote

import com.agb.core.data.UserDataSource
import com.agb.core.domain.model.User
import com.agb.lemon_android.frameworks.datasource.remote.api.LemonApi

class UserRemoteDataSource(private val api: LemonApi) : UserDataSource {
    override suspend fun getUserInfo(): User {
        TODO("Not yet implemented")
    }

    override suspend fun saveUserInfo(user: User) {
        TODO("Not yet implemented")
    }
}
