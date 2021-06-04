package com.agb.feature_cards.data.local

import android.util.Log
import com.agb.core.common.Result
import com.agb.core.common.exceptions.UnexpectedLemonException
import com.agb.feature_cards.core.datasource.CardsDataSource
import com.agb.feature_cards.core.domain.models.Card
import com.agb.feature_cards.data.local.db.CardsDao
import com.agb.feature_cards.data.local.db.CardsEntity
import com.agb.feature_cards.data.local.db.toCards
import com.agb.feature_cards.data.local.db.toCardsEntity

class CardsLocalDataSource(private val cardsDao: CardsDao) : CardsDataSource {
    private val tag = this::class.java.simpleName

    override suspend fun getCards(login: String): Result<List<Card>> = try {
        val cardsList = cardsDao.getCards()
        if (cardsList.isNotEmpty()) {
            val mapped = cardsList.map(CardsEntity::toCards)
            Result.Success(mapped)
        } else {
            Result.Error(UnexpectedLemonException("No cards cached for user $login"))
        }
    } catch (e: Exception) {
        Log.e(tag, "getCardsLocal: ${e.message}")
        Result.Error(UnexpectedLemonException("Can't get cards locally"))
    }

    override suspend fun saveCards(login: String, cards: List<Card>) = try {
        val cardsEntities = cards.map { card -> card.toCardsEntity(login) }
        cardsDao.insertAll(cardsEntities)
        Result.Ok
    } catch (e: Exception) {
        Log.e(tag, "saveCardsLocal: ${e.message}")
        Result.Error(UnexpectedLemonException("Can't cache cards"))
    }
}
