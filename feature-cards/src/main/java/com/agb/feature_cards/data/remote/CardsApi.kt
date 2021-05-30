package com.agb.feature_cards.data.remote

import com.agb.feature_cards.core.domain.models.Card
import retrofit2.http.GET

interface CardsApi {
    @GET("/cards")
    suspend fun getCards(): List<Card>
}
