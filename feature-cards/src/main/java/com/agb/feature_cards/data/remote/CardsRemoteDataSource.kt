package com.agb.feature_cards.data.remote

import com.agb.core.common.Result
import com.agb.core.common.exceptions.UnexpectedLemonException
import com.agb.data.remote.HttpExceptionHandler
import com.agb.feature_cards.core.datasource.CardsDataSource
import com.agb.feature_cards.core.domain.models.Card
import retrofit2.HttpException

class CardsRemoteDataSource(private val api: CardsApi) : CardsDataSource, HttpExceptionHandler {
    override suspend fun getCards(): Result<List<Card>> {
        return try {
            Result.Success(api.getCards())
        } catch (e: Exception) {
            when (e) {
                is HttpException -> Result.Error(handleHttpException(e))
                else -> Result.Error(UnexpectedLemonException(e))
            }
        }
    }
}
