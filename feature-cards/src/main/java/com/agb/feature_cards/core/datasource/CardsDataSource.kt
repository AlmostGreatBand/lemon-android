package com.agb.feature_cards.core.datasource

import com.agb.core.common.Result
import com.agb.feature_cards.core.domain.models.Card

interface CardsDataSource {
    suspend fun getCards(): Result<List<Card>>
}
