package com.agb.feature_cards.core.domain.repository

import com.agb.core.common.orElse
import com.agb.feature_cards.core.datasource.CardsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CardsRepository(
    private val local: CardsDataSource,
    private val remote: CardsDataSource,
) {
    suspend fun getCards(login: String) = withContext(Dispatchers.IO) {
        remote.getCards(login)
            .onSuccess { cards -> local.saveCards(login, cards) }
            .orElse { local.getCards(login) }
    }
}
