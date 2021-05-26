package com.agb.feature_login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agb.core.common.Operation
import com.agb.core.common.Result
import com.agb.core.common.exceptions.LogicError
import com.agb.core.domain.interactor.UserInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userInteractor: UserInteractor
) : ViewModel() {
    private val _buttonEnabled = MutableStateFlow(false)
    val buttonEnabled: StateFlow<Boolean> get() = _buttonEnabled

    val login = MutableStateFlow("")
    val password = MutableStateFlow("")

    private val _loginStatus = MutableStateFlow<Operation?>(null)
    val loginStatus: StateFlow<Operation?> get() = _loginStatus

    init {
        val checkValidity = {
            _buttonEnabled.value = credentialsValid
        }

        viewModelScope.launch {
            login.collect { checkValidity() }
            password.collect { checkValidity() }
        }
    }

    private val credentialsValid: Boolean
        get() = when {
            login.value.isBlank() -> false
            password.value.isBlank() -> false
            else -> true
        }

    fun login() {
        if (!credentialsValid) {
            _loginStatus.value = Result.Error(LogicError.ValidationError)
            return
        }

        _buttonEnabled.value = false
        _loginStatus.value = Result.Pending
        viewModelScope.launch {
            val res = userInteractor.login(login.value, password.value)
            _loginStatus.emit(res)
        }
    }
}
