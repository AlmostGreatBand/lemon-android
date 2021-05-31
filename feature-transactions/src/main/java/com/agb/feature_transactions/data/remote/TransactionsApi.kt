package com.agb.feature_transactions.data.remote

import com.agb.feature_transactions.core.domain.models.Transaction
import retrofit2.http.GET

interface TransactionsApi {
    @GET("/transactions")
    fun getTransactions(): List<Transaction>
}
