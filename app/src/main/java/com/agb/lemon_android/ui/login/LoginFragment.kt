package com.agb.lemon_android.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.agb.lemon_android.R
import com.agb.lemon_android.ui.MainActivity
import com.agb.lemon_android.ui.registration.SignUpFragment
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {
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

        login_login.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                vm.buttonLiveData.value = true
            }
        })

        vm.buttonLiveData.observe(viewLifecycleOwner, Observer { login_button.isEnabled=it })

        login_button.setOnClickListener {
            vm.login()

            val name = vm.userLiveData.value?.name
            val login = vm.userLiveData.value?.login
            val password = vm.userLiveData.value?.password

            createToast("$name $login $password")
        }

        login_to_registration.setOnClickListener{
            (requireActivity() as MainActivity).changeFragment(SignUpFragment())
        }
    }

    private fun createToast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
