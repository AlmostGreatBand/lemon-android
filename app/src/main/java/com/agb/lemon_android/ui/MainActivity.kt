package com.agb.lemon_android.ui

import android.os.Bundle
import com.agb.core_ui.LemonActivity
import com.agb.feature_login.ui.LoginFragment

class MainActivity : LemonActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeFragment(LoginFragment())
    }
}
