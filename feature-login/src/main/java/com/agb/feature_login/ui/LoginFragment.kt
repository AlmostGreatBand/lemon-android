package com.agb.feature_login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.agb.core.common.Result
import com.agb.core.common.Stage
import com.agb.core_ui.LemonFragment
import com.agb.feature_login.R
import com.agb.feature_login.databinding.FragmentLoginBinding
import com.agb.feature_login.di.AuthModule
import com.agb.feature_login.di.DaggerLoginComponent

class LoginFragment : LemonFragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        DaggerLoginComponent.builder()
            .authModule(AuthModule(lemonApp))
            .appModule(appModule)
            .build()
            .inject(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.buttonEnabled.observe(viewLifecycleOwner, binding.loginBtn::setEnabled)
        viewModel.loginStatus.observe(viewLifecycleOwner) {
            binding.progress.isVisible = false
            when (it) {
                Result.Pending -> binding.progress.isVisible = true
                is Result.Error -> shortToast(
                    getString(R.string.login_error, it.exception)
                )
                is Result.Success -> router routeTo Stage.Home
            }
        }

        binding.loginBtn.setOnClickListener { viewModel.login() }
        binding.loginField.addTextChangedListener {
            viewModel.login.value = it?.toString() ?: ""
        }
        binding.passField.addTextChangedListener {
            viewModel.password.value = it?.toString() ?: ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
