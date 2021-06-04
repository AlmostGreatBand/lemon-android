package com.agb.feature_home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agb.core.common.Result
import com.agb.feature_cards.core.datasource.CardsDataSource
import com.agb.feature_cards.core.domain.models.Card
import com.agb.feature_transactions.core.datasource.TransactionsDataSource
import com.agb.feature_transactions.core.domain.models.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val cardsDS: CardsDataSource,
    private val transactionsDS: TransactionsDataSource,
) : ViewModel() {
    private val _itemsFlow = MutableStateFlow<Result<Map<Card, List<Transaction>>>>(Result.Pending)
    val itemsFlow: StateFlow<Result<Map<Card, List<Transaction>>>> get() = _itemsFlow

    fun getCardsWithTransactions() {
        viewModelScope.launch {
            launch {
                val cards = cardsDS.getCards("")
                val transactions = transactionsDS.getTransactions()

                when {
                    cards is Result.Success && transactions is Result.Success -> {
                        val grouped = transactions.data.groupBy { it.cardId }
                        val res = cards.data.map { it to (grouped[it.cardId] ?: listOf()) }.toMap()
                        _itemsFlow.emit(Result.Success(res))
                    }
                    cards is Result.Error -> {
                        _itemsFlow.emit(cards)
                    }
                    transactions is Result.Error -> {
                        _itemsFlow.emit(transactions)
                    }
                }
            }
        }
    }
}
