package com.agb.feature_profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agb.core.common.Operation
import com.agb.core.common.Result
import com.agb.core.domain.interactor.UserInteractor
import com.agb.core.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class ProfileViewModel(
    private val userInteractor: UserInteractor
) : ViewModel() {
    private var user = User("", "", "")

    private val _userFlow = MutableStateFlow<Result<User>>(Result.Pending)
    val userFlow: StateFlow<Result<User>> get() = _userFlow

    private val _updateFlow = MutableStateFlow<Operation>(Result.Pending)
    val updateFlow: StateFlow<Operation> get() = _updateFlow

    private val _showConfirmPass = MutableStateFlow(false)
    val showConfirmPass: StateFlow<Boolean> get() = _showConfirmPass

    private val _saveButtonEnabled = MutableStateFlow(false)
    val saveButtonEnabled: StateFlow<Boolean> get() = _saveButtonEnabled

    private val _logoutFlow = MutableStateFlow<Operation>(Result.Pending)
    val logoutFlow: StateFlow<Operation> get() = _logoutFlow

    val usernameFlow = MutableStateFlow("")
    val loginFlow = MutableStateFlow("")
    val passwordFlow = MutableStateFlow("")
    val passwordConfFlow = MutableStateFlow("")

    init {
        val validate = {
            val username = usernameFlow.value
            val login = loginFlow.value
            val pass = passwordFlow.value
            val passConf = passwordConfFlow.value
            _showConfirmPass.value = when {
                pass != user.password && pass.isNotBlank() -> true
                else -> false
            }

            _saveButtonEnabled.value = when {
                username.isNotBlank() && username != user.name -> true
                login.isNotBlank() && login != user.login -> true
                pass.isNotBlank() && pass == passConf -> true
                else -> false
            }
        }

        viewModelScope.launch {
            launch { usernameFlow.collect { validate() } }
            launch { loginFlow.collect { validate() } }
            launch { passwordFlow.collect { validate() } }
            launch { passwordConfFlow.collect { validate() } }
        }
    }

    fun loadUser() {
        viewModelScope.launch {
            _userFlow.value = userInteractor.getUser().onSuccess {
                user = it
            }
        }
    }

    fun updateUser() {
        val name = usernameFlow.value.let {
            if (it.isBlank()) user.name else it
        }

        val login = loginFlow.value.let {
            if (it.isBlank()) user.login else it
        }

        val pass = passwordFlow.value.let {
            if (it.isBlank()) user.password else it
        }

        val user = User(name, login, pass)
        viewModelScope.launch {
            _updateFlow.value = userInteractor
                .saveUser(user)
                .onSuccess {
                    _userFlow.value = Result.Success(user)
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _logoutFlow.value = userInteractor.logout()
        }
    }
}
