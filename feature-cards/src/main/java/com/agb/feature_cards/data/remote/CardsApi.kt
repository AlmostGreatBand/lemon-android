package com.agb.feature_cards.data.remote

import retrofit2.http.GET

interface CardsApi {
    @GET("/cards")
    suspend fun getCards(): CardsResponse
}
