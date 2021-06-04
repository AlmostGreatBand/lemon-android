package com.agb.feature_cards.data.remote

import com.agb.feature_cards.data.models.CardsResponse
import retrofit2.http.GET

interface CardsApi {
    @GET("/cards")
    suspend fun getCards(): CardsResponse
}
