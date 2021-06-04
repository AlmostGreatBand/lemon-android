package com.agb.feature_transactions.core.domain.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Transaction(
    @SerializedName("transaction_id")
    val transactionId: Int,
    @SerializedName("card_id")
    val cardId: Int,
    val amount: Int,
    val type: String,
    val date: Date,
)
