package com.agb.feature_transactions.data.remote

import com.agb.feature_transactions.data.models.TransactionsResponse
import retrofit2.http.GET

interface TransactionsApi {
    @GET("/transactions")
    suspend fun getTransactions(): TransactionsResponse
}
