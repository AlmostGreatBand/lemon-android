package com.agb.feature_home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agb.core.common.Result
import com.agb.core.domain.model.User
import com.agb.feature_login.core.interactor.UserUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    @Inject
    lateinit var useCase: UserUseCase.GetUser

    private val _userFlow = MutableStateFlow<Result<User>>(Result.Pending)
    val userFlow: StateFlow<Result<User>> get() = _userFlow

    fun getName() {
        viewModelScope.launch {
            _userFlow.emit(useCase())
        }
    }
}
