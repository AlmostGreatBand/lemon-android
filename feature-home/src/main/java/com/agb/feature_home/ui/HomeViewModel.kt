package com.agb.feature_home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agb.core.common.Result
import com.agb.feature_cards.core.datasource.CardsDataSource
import com.agb.feature_cards.core.domain.models.Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val cardsDS: CardsDataSource) : ViewModel() {
    private val _cardsFlow = MutableStateFlow<Result<List<Card>>>(Result.Pending)
    val cardsFlow: StateFlow<Result<List<Card>>> get() = _cardsFlow

    fun getCards() {
        viewModelScope.launch {
            val cards = cardsDS.getCards()
            _cardsFlow.emit(cards)
        }
    }
}
