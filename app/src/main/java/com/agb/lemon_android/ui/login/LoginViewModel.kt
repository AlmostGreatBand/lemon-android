package com.agb.lemon_android.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agb.core.domain.model.User
import com.agb.lemon_android.frameworks.datasource.remote.api.getAuthService
import kotlinx.coroutines.launch
import okhttp3.Credentials

class LoginViewModel () : ViewModel() {
    val api = getAuthService()
    val loginLiveData = MutableLiveData<String>()
    val passwordLiveData = MutableLiveData<String>()
    val buttonLiveData = MutableLiveData(false)
    val userLiveData = MutableLiveData<User>()

    fun login() {
        val login = loginLiveData.value
        val password = passwordLiveData.value

        buttonLiveData.value = false
        viewModelScope.launch {
            userLiveData.postValue(api.login(Credentials.basic(login, password)))
            buttonLiveData.postValue(true)
        }
    }
}