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
import com.agb.feature_home.ui.cards.CardsAdapter
import com.agb.feature_home.ui.cards.CardsItemDecorator
import com.agb.feature_home.ui.transactions.TransactionsPageAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : LemonFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()
    private var cardsAdapter: CardsAdapter? = null
    private var transactionsPageAdapter: TransactionsPageAdapter? = null

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
        setUpTransactionsPageAdapter()

        binding.profileBtn.setOnClickListener {
            router.routeTo(Stage.Profile)
        }

        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.itemsFlow.collect {
                    when (it) {
                        is Result.Success -> {
                            val cards = it.data.keys.toList()
                            cardsAdapter?.updateDataSet(cards)
                            transactionsPageAdapter?.updateDataSet(cards, it.data)
                        }
                        else -> Unit
                    }
                }
            }
        }

        viewModel.getCardsWithTransactions()
    }

    private fun setUpCardsAdapter() {
        binding.cards.getChildAt(0).setOnTouchListener { _, event ->
            binding.transactionPages.getChildAt(0).onTouchEvent(event)
            false
        }
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

    private fun setUpTransactionsPageAdapter() {
        binding.transactionPages.getChildAt(0).setOnTouchListener { _, event ->
            binding.cards.getChildAt(0).onTouchEvent(event)
            false
        }
        transactionsPageAdapter = TransactionsPageAdapter()
        binding.transactionPages.adapter = transactionsPageAdapter
        binding.cards.offscreenPageLimit = 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
