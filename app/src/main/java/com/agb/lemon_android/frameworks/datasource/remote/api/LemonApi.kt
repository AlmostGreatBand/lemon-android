package com.agb.lemon_android.frameworks.datasource.remote.api

import com.agb.core.domain.model.Card
import com.agb.core.domain.model.Transaction
import com.agb.core.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LemonApi {
    @POST("profile")
    suspend fun updateProfile(@Body user: User)

    @GET("profile")
    suspend fun getProfile(): User

    @GET("cards")
    suspend fun getCards(): List<Card>

    @GET("transactions")
    suspend fun getTransactions(): List<Transaction>
}
