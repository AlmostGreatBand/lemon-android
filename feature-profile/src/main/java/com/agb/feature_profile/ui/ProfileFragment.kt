package com.agb.feature_profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.agb.core.common.Result
import com.agb.core_ui.LemonFragment
import com.agb.feature_profile.R
import com.agb.feature_profile.databinding.FragmentProfileBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : LemonFragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        configureViewModel()
        configureUi()
    }

    private fun configureUi() = with(binding) {
        usernameEdx.addTextChangedListener {
            viewModel.usernameFlow.value = it?.toString() ?: ""
        }

        loginEdx.addTextChangedListener {
            viewModel.loginFlow.value = it?.toString() ?: ""
        }

        passwordEdx.addTextChangedListener {
            viewModel.passwordFlow.value = it?.toString() ?: ""
        }

        passwordConfEdx.addTextChangedListener {
            viewModel.passwordConfFlow.value = it?.toString() ?: ""
        }

        updateBtn.setOnClickListener {
            viewModel.updateUser()
        }
    }

    private fun configureViewModel() = with(binding) {
        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.userFlow.collect {
                    when (it) {
                        is Result.Success -> {
                            val user = it.data
                            usernameEdx.setText(user.name)
                            loginEdx.setText(user.login)
                            title.text = getString(R.string.profile_title, user.name)
                        }
                        is Result.Error -> {
                            shortToast(it.exception.localizedMessage ?: "")
                        }
                        Result.Pending -> Unit
                    }
                }
            }

            launch {
                viewModel.showConfirmPass.collect {
                    if (it) {
                        motionPasswordConf.transitionToEnd()
                    } else {
                        motionPasswordConf.transitionToStart()
                    }
                }
            }

            launch {
                viewModel.saveButtonEnabled.collect {
                    updateBtn.isEnabled = it
                    if (it) {
                        motionButton.transitionToEnd()
                    } else {
                        motionButton.transitionToStart()
                    }
                }
            }

            launch {
                viewModel.updateFlow.collect {
                    when (it) {
                        is Result.Success -> {
                            updateBtn.isEnabled = viewModel.saveButtonEnabled.value
                            shortToast(R.string.profile_updated)
                        }
                        is Result.Error -> {
                            updateBtn.isEnabled = viewModel.saveButtonEnabled.value
                            shortToast(
                                getString(
                                    R.string.profile_update_error,
                                    it.exception.message
                                )
                            )
                        }
                        Result.Pending -> {
                            updateBtn.isEnabled = false
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
