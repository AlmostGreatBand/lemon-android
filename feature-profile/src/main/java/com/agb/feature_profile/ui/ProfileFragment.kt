package com.agb.feature_profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.agb.core.common.Operation
import com.agb.core.common.Result
import com.agb.core.common.Stage
import com.agb.core.domain.model.User
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

        updateBtn.setOnClickListener { viewModel.updateUser() }

        logoutBtn.setOnClickListener { viewModel.logout() }
    }

    private fun configureViewModel() = with(viewModel) {
        lifecycleScope.launchWhenCreated {
            launch { userFlow.collect(this@ProfileFragment::userDataReceived) }
            launch { showConfirmPass.collect(this@ProfileFragment::showConfirmPass) }
            launch { saveButtonEnabled.collect(this@ProfileFragment::showUpdateButton) }
            launch { updateFlow.collect(this@ProfileFragment::onUpdate) }
            launch { logoutFlow.collect(this@ProfileFragment::onLogout) }
        }
    }

    private fun userDataReceived(result: Result<User>) {
        when (result) {
            is Result.Success -> {
                val user = result.data
                binding.usernameEdx.setText(user.name)
                binding.loginEdx.setText(user.login)
                binding.title.text = getString(R.string.profile_title, user.name)
            }
            is Result.Error -> {
                shortToast(result.exception.localizedMessage ?: "")
            }
            Result.Pending -> Unit
        }
    }

    private fun showConfirmPass(show: Boolean) {
        if (show) {
            binding.motionPasswordConf.transitionToEnd()
        } else {
            binding.motionPasswordConf.transitionToStart()
        }
    }

    private fun showUpdateButton(show: Boolean) {
        binding.updateBtn.isEnabled = show
        if (show) {
            binding.motionButton.transitionToEnd()
        } else {
            binding.motionButton.transitionToStart()
        }
    }

    private fun onUpdate(result: Operation) {
        when (result) {
            is Result.Success -> {
                binding.updateBtn.isEnabled = viewModel.saveButtonEnabled.value
                shortToast(R.string.profile_updated)
            }
            is Result.Error -> {
                binding.updateBtn.isEnabled = viewModel.saveButtonEnabled.value
                shortToast(
                    getString(
                        R.string.profile_update_error,
                        result.exception.message
                    )
                )
            }
            Result.Pending -> binding.updateBtn.isEnabled = false
        }
    }

    private fun onLogout(result: Operation) {
        when (result) {
            is Result.Success -> router.routeTo(Stage.Login, true)
            Result.Pending -> Unit
            is Result.Error -> {
                shortToast(getString(R.string.profile_logout_error, result.exception.message))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
