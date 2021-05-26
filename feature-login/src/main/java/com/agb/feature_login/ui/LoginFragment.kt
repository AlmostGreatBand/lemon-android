package com.agb.feature_login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.agb.core.common.Result
import com.agb.core.common.Stage
import com.agb.core_ui.LemonFragment
import com.agb.feature_login.R
import com.agb.feature_login.databinding.FragmentLoginBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : LemonFragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModel()

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

        lifecycleScope.launchWhenStarted {
            viewModel.buttonEnabled.collect(binding.loginBtn::setEnabled)
            viewModel.loginStatus.collect {
                binding.progress.isVisible = false
                when (it) {
                    Result.Pending -> binding.progress.isVisible = true
                    is Result.Error -> shortToast(
                        getString(R.string.login_error, it.exception)
                    )
                    is Result.Success -> router.routeTo(Stage.Home, true)
                    null -> Unit
                }
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
