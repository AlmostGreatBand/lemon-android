package com.agb.feature_cards.core.domain.repository

import com.agb.core.common.orElse
import com.agb.feature_cards.core.datasource.CardsDataSource

class CardsRepository(
    private val local: CardsDataSource,
    private val remote: CardsDataSource,
) {
    suspend fun getCards(login: String) = remote.getCards(login)
        .onSuccess { cards -> local.saveCards(login, cards) }
        .orElse { local.getCards(login) }
}
