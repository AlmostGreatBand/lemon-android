package com.agb.feature_cards.data.remote

import android.util.Log
import com.agb.core.common.Result
import com.agb.core.common.exceptions.UnexpectedLemonException
import com.agb.data.remote.HttpExceptionHandler
import com.agb.feature_cards.core.datasource.CardsDataSource
import com.agb.feature_cards.core.domain.models.Card
import retrofit2.HttpException

class CardsRemoteDataSource(private val api: CardsApi) : CardsDataSource, HttpExceptionHandler {
    private val tag = this::class.java.simpleName

    override suspend fun getCards(login: String): Result<List<Card>> {
        return try {
            Result.Success(api.getCards().cards)
        } catch (e: Exception) {
            Log.e(tag, "getCardsRemote: ${e.message}")
            Result.Error(UnexpectedLemonException("Can't get cards remotely for user $login"))
            when (e) {
                is HttpException -> Result.Error(handleHttpException(e))
                else -> Result.Error(UnexpectedLemonException(e))
            }
        }
    }

    override suspend fun saveCards(login: String, cards: List<Card>) = Result.Error("Stub!")
}
