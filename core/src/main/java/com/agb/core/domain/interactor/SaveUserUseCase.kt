package com.agb.core.domain.interactor

import com.agb.core.domain.model.User
import com.agb.core.domain.repository.user.UserRepository

class SaveUserUseCase(private val repository: UserRepository) {
    operator fun invoke(user: User) {
        println(user) // just to suppress "is never used" warning
        // TODO("implement user info saving")
    }
}
