package com.agb.feature_home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.agb.core.common.Result
import com.agb.core.common.Stage
import com.agb.core_ui.LemonFragment
import com.agb.feature_home.R
import com.agb.feature_home.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : LemonFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.userFlow.collect {
                when (it) {
                    is Result.Success -> title(it.data.name)
                    Result.Pending -> defaultTitle()
                    is Result.Error -> {
                        defaultTitle()
                        shortToast(getString(R.string.home_error, it.exception))
                    }
                }
            }
        }

        binding.profileButton.setOnClickListener {
            router.routeTo(Stage.Profile)
        }

        viewModel.getName()
    }

    private fun defaultTitle() {
        binding.title.text = getString(R.string.home_title, getString(R.string.home_default_name))
    }

    private fun title(username: String) {
        binding.title.text = getString(R.string.home_title, username)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
