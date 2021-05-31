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
    private val _cardsFlow = MutableStateFlow<Result<List<Card>>>(Result.Pending)
    val cardsFlow: StateFlow<Result<List<Card>>> get() = _cardsFlow
    private val _transactionsFlow = MutableStateFlow<Result<List<Transaction>>>(Result.Pending)
    val transactionsFlow: StateFlow<Result<List<Transaction>>> get() = _transactionsFlow

    fun getCards() {
        viewModelScope.launch {
            launch {
                val cards = cardsDS.getCards()
                _cardsFlow.emit(cards)
            }
            launch {
                val transactions = transactionsDS.getTransactions()
                _transactionsFlow.emit(transactions)
            }
        }
    }
}

//private val _itemsFlow = MutableStateFlow<Result<List<Pair<Card, List<Transaction>>>>>(Result.Pending)
//val itemsFlow: StateFlow<Result<List<Pair<Card, List<Transaction>>>>> get() = _itemsFlow
//
//fun getCards() {
//    viewModelScope.launch {
//        val cards = cardsDS.getCards()
//        val transactions = transactionsDS.getTransactions()
//
//        when {
//            cards is Result.Success && transactions is Result.Success -> {
//                val grouped = transactions.data.groupBy { it.cardId }
//                val res = cards.data.map { it to (grouped[it.cardId] ?: emptyList()) }
//                _itemsFlow.emit(Result.Success(res))
//            }
//            cards is Result.Error -> {
//                _itemsFlow.emit(cards)
//            }
//            transactions is Result.Error -> {
//                _itemsFlow.emit(transactions)
//            }
//        }
//    }
//}
