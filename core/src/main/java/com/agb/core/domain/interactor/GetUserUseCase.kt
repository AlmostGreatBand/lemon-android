package com.agb.core.domain.interactor

import com.agb.core.domain.model.User
import com.agb.core.domain.repository.user.UserRepository

class GetUserUseCase(private val repository: UserRepository) {
    operator fun invoke(): User {
        // TODO("implement user info saving")
        return User("aleh", "aleh123", "aleh")
    }
}
