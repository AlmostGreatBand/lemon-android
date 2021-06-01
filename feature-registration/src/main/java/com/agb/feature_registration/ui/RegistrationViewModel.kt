package com.agb.feature_registration.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agb.core.common.Operation
import com.agb.core.common.Result
import com.agb.core.domain.interactor.UserInteractor
import com.agb.core.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val userInteractor: UserInteractor,
    coroutineScope: CoroutineScope? = null,
) : ViewModel() {
    private val scope = coroutineScope ?: viewModelScope

    private val _status = MutableStateFlow<Operation?>(null)
    val status: StateFlow<Operation?> get() = _status

    private val _isDataValid = MutableStateFlow(false)
    val isDataValid: StateFlow<Boolean> get() = _isDataValid

    val username = MutableStateFlow("")
    val login = MutableStateFlow("")
    val password = MutableStateFlow("")

    init {
        val validate = {
            val fields = listOf(
                username.value,
                login.value,
                password.value
            )

            _isDataValid.value = fields.none { it.isBlank() }
        }

        with(scope) {
            launch { login.collect { validate() } }
            launch { username.collect { validate() } }
            launch { password.collect { validate() } }
        }
    }

    fun register() {
        val username = username.value
        val login = login.value
        val password = password.value
        _status.value = Result.Pending
        scope.launch {
            _status.value = userInteractor.register(User(username, login, password))
        }
    }
}
