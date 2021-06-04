package com.agb.feature_transactions.core.datasource

import com.agb.core.common.Result
import com.agb.feature_transactions.core.domain.models.Transaction

interface TransactionsDataSource {
    suspend fun getTransactions(): Result<List<Transaction>>
}
