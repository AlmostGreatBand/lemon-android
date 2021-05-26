package com.agb.core.domain.interactor

import com.agb.core.domain.model.User
import com.agb.core.domain.repository.UserRepository

class UserInteractor(private val repository: UserRepository) {
    suspend fun login(login: String, password: String) = repository.login(login, password)
    suspend fun getUser() = repository.getUserInfo()
    suspend fun saveUser(user: User) = repository.saveUserInfo(user)
    suspend fun logout() {
        TODO("Not yet implemented")
    }
}
