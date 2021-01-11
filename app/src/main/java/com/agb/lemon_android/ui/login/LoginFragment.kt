package com.agb.lemon_android.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.agb.lemon_android.R
import com.agb.lemon_android.ui.LemonFragment
import com.agb.lemon_android.ui.main.MainFragment
import com.agb.lemon_android.ui.registration.SignUpFragment
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : LemonFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    private val vm by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_login.addTextChangedListener { vm.buttonLiveData.value = true }

        vm.buttonLiveData.observe(viewLifecycleOwner, { login_button.isEnabled=it })

        login_button.setOnClickListener {
            vm.loginLiveData.value = login_login.text.toString()
            vm.passwordLiveData.value = login_password.text.toString()
            vm.login()
        }

        vm.userLiveData.observe(viewLifecycleOwner, {
            createToast("${it.name} ${it.login} ${it.password}")
            changeFragment(MainFragment())
        })

        login_to_registration.setOnClickListener{ changeFragment(SignUpFragment()) }
    }
}