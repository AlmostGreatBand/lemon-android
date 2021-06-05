package com.agb.feature_home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agb.core.common.Result
import com.agb.core.datasource.UserDataSource
import com.agb.feature_cards.core.domain.models.Card
import com.agb.feature_cards.core.domain.repository.CardsRepository
import com.agb.feature_transactions.core.domain.models.Transaction
import com.agb.feature_transactions.core.domain.repository.TransactionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val cardsRepository: CardsRepository,
    private val transactionsRepository: TransactionsRepository,
    private val user: UserDataSource,
) : ViewModel() {
    private val _itemsFlow = MutableStateFlow<Result<Map<Card, List<Transaction>>>>(Result.Pending)
    val itemsFlow: StateFlow<Result<Map<Card, List<Transaction>>>> get() = _itemsFlow

    fun getCardsWithTransactions() {
        viewModelScope.launch {
            val user = user.getUserInfo()
            if (user is Result.Success) {
                val login = user.data.login
                val cards = cardsRepository.getCards(login)
                val transactions = transactionsRepository.getTransactions(login)

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
            } else {
                val exc = Result.Error("Can't current get user info")
                _itemsFlow.emit(exc)
            }
        }
    }
}
