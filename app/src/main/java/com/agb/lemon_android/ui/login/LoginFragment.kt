package com.agb.lemon_android.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.agb.lemon_android.R
import com.agb.lemon_android.ui.MainActivity
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_button.setOnClickListener {
            val login = login_login.text.toString()
            val password = login_password.text.toString()
            createToast("$login $password")
        }

        login_to_registration.setOnClickListener{
            (requireActivity() as MainActivity).changeFragment(SignUpFragment())
        }
    }

    private fun createToast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
