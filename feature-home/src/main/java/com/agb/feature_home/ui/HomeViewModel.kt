package com.agb.feature_home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agb.core.common.Result
import com.agb.core.domain.interactor.UserInteractor
import com.agb.core.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val userInteractor: UserInteractor) : ViewModel() {
    private val _userFlow = MutableStateFlow<Result<User>>(Result.Pending)
    val userFlow: StateFlow<Result<User>> get() = _userFlow

    fun getName() {
        viewModelScope.launch {
            val user = userInteractor.getUser()
            _userFlow.emit(user)
        }
    }
}
