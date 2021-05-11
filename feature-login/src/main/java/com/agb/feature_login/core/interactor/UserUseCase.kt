package com.agb.feature_login.core.interactor

import com.agb.core.common.Operation
import com.agb.core.domain.model.User
import com.agb.feature_login.core.repository.UserRepository
import javax.inject.Inject

sealed class UserUseCase constructor(protected val repository: UserRepository) {
    class Login @Inject constructor(repository: UserRepository) : UserUseCase(repository) {
        suspend operator fun invoke(login: String, password: String): Operation {
            return repository.login(login, password)
        }
    }

    class GetUser @Inject constructor(repository: UserRepository) : UserUseCase(repository) {
        operator fun invoke(): User? = repository.currentUser
    }

    class SaveUser @Inject constructor(repository: UserRepository) : UserUseCase(repository) {
        suspend operator fun invoke(user: User): Operation = repository.saveUserInfo(user)
    }

    class Logout @Inject constructor(repository: UserRepository) : UserUseCase(repository) {
        operator fun invoke() = repository.clear()
    }
}
