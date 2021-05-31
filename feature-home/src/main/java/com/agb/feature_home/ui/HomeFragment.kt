package com.agb.feature_home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.agb.core.common.Result
import com.agb.core.common.Stage
import com.agb.core_ui.LemonFragment
import com.agb.core_ui.utils.dp
import com.agb.feature_home.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : LemonFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()
    private var cardsAdapter: CardsAdapter? = null

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

        setUpCardsAdapter()

        binding.profileBtn.setOnClickListener {
            router.routeTo(Stage.Profile)
        }

        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.cardsFlow.collect {
                    when (it) {
                        is Result.Success -> cardsAdapter?.updateDataSet(it.data)
                        else -> Unit
                    }
                }
            }
            launch {
                viewModel.transactionsFlow.collect {
                    when (it) {
                        is Result.Success -> cardsAdapter?.updateDataSet(it.data)
                        else -> Unit
                    }
                }
            }
        }

        viewModel.getCards()
    }

    private fun setUpCardsAdapter() {
        cardsAdapter = CardsAdapter()
        binding.cards.adapter = cardsAdapter
        binding.cards.offscreenPageLimit = 1
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = (-60).dp * position
        }
        binding.cards.setPageTransformer(pageTransformer)

        val itemDecoration = CardsItemDecorator()
        binding.cards.addItemDecoration(itemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
