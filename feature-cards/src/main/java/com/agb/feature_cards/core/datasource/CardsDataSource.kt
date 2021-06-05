package com.agb.feature_cards.core.datasource

import com.agb.core.common.Operation
import com.agb.core.common.Result
import com.agb.feature_cards.core.domain.models.Card

interface CardsDataSource {
    suspend fun getCards(login: String): Result<List<Card>>
    suspend fun saveCards(login: String, cards: List<Card>): Operation
}
