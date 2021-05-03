package com.agb.core.domain.interactor

import com.agb.core.common.Operation
import com.agb.core.domain.model.User
import com.agb.core.domain.repository.user.UserRepository

sealed class UserUseCase(
    protected val repository: UserRepository
) {
    class Login(repository: UserRepository) : UserUseCase(repository) {
        suspend operator fun invoke(login: String, password: String): Operation {
            return repository.login(login, password)
        }
    }

    class GetUser(repository: UserRepository) : UserUseCase(repository) {
        operator fun invoke(): User? = repository.currentUser
    }

    class SaveUser(repository: UserRepository) : UserUseCase(repository) {
        suspend operator fun invoke(user: User): Operation = repository.saveUserInfo(user)
    }

    class Logout(repository: UserRepository) : UserUseCase(repository) {
        operator fun invoke() = repository.clear()
    }
}
