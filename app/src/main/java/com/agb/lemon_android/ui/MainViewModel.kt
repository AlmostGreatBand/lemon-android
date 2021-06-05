package com.agb.lemon_android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agb.core.common.Result
import com.agb.core.common.Stage
import com.agb.core.common.exceptions.LemonException
import com.agb.core.domain.interactor.UserInteractor
import com.agb.core.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val interactor: UserInteractor
) : ViewModel() {
    val stages = mutableListOf<Stage>()

    val stage get() = stages.last()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    private val _authError = MutableStateFlow<LemonException?>(null)
    val authError: StateFlow<LemonException?> get() = _authError

    fun loadUserData() {
        viewModelScope.launch {
            when (val res = interactor.getUser()) {
                is Result.Success -> res.data.let { user ->
                    interactor.login(user.login, user.password).onError {
                        _authError.value = it
                    }.onSuccess {
                        _user.value = user
                    }
                }
                is Result.Error -> _authError.value = res.exception
                Result.Pending -> TODO()
            }
        }
    }
}
