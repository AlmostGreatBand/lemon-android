package com.agb.lemon_android.frameworks.datasource.remote.api

import com.agb.core.domain.model.Card
import com.agb.core.domain.model.Transaction
import com.agb.core.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LemonApi {
    @POST("registration")
    suspend fun registration(@Body user: User)

    @POST("profile")
    suspend fun updateProfile(@Body user: User)

    @GET("profile")
    suspend fun getProfile(@Header("Authorization") credentials: String): User

    @GET("cards")
    suspend fun getCards(@Header("Authorization") credentials: String): List<Card>

    @GET("transactions")
    suspend fun getTransactions(@Header("Authorization") credentials: String): List<Transaction>
}
