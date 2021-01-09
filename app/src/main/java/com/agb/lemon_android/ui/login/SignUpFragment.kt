package com.agb.lemon_android.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.agb.lemon_android.R
import com.agb.lemon_android.ui.MainActivity
import kotlinx.android.synthetic.main.sign_up_fragment.*

class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        send_button.setOnClickListener {
            val username = registration_username.text.toString()
            val login = registration_login.text.toString()
            val password = registration_password.text.toString()
            createToast("$username $login $password")
        }

        registration_to_login.setOnClickListener{
            (requireActivity() as MainActivity).changeFragment(LoginFragment())
        }
    }
    private fun createToast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
