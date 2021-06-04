package com.agb.feature_transactions.data.models

import com.agb.feature_transactions.core.domain.models.Transaction

data class TransactionsResponse(
    val transactions: List<Transaction>
)
