package com.agb.feature_registration.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.agb.core.common.Result
import com.agb.core.common.Stage
import com.agb.core_ui.LemonFragment
import com.agb.core_ui.bindTo
import com.agb.feature_registration.databinding.FragmentRegistrationBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : LemonFragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistrationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        usernameEdx bindTo viewModel.username
        loginEdx bindTo viewModel.login
        passwordEdx bindTo viewModel.password

        sendBtn.setOnClickListener { viewModel.register() }
        loginBtn.setOnClickListener { router.back() }

        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.isDataValid.collect { binding.sendBtn.isEnabled = it }
            }
            launch {
                viewModel.status.collect {
                    binding.sendBtn.isEnabled = viewModel.isDataValid.value
                    when (it) {
                        is Result.Success -> router.routeTo(Stage.Home)
                        is Result.Error -> shortToast(it.exception.localizedMessage ?: "Error")
                        Result.Pending -> binding.sendBtn.isEnabled = false
                        null -> Unit
                    }
                }
            }
        }

        return@with
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
