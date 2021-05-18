package com.agb.feature_login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agb.core.common.Operation
import com.agb.core.common.Result
import com.agb.core.common.exceptions.LogicError
import com.agb.feature_login.core.interactor.UserUseCase
import javax.inject.Inject
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _buttonEnabled = MutableLiveData(false)
    val buttonEnabled: LiveData<Boolean> get() = _buttonEnabled

    val login = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginStatus = MutableLiveData<Operation>()
    val loginStatus: LiveData<Operation> get() = _loginStatus

    @Inject
    lateinit var useCase: UserUseCase.Login

    init {
        val checkValidity = {
            _buttonEnabled.value = credentialsValid
        }
        login.observeForever { checkValidity() }
        password.observeForever { checkValidity() }
    }

    private val credentialsValid: Boolean
        get() = when {
            login.value.isNullOrBlank() -> false
            password.value.isNullOrBlank() -> false
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
            val res = useCase(login.value ?: "", password.value ?: "")
            _loginStatus.postValue(res)
        }
    }
}
