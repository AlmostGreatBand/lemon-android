package com.agb.feature_transactions.core.datasource

import com.agb.core.common.Operation
import com.agb.core.common.Result
import com.agb.feature_transactions.core.domain.models.Transaction

interface TransactionsDataSource {
    suspend fun getTransactions(login: String): Result<List<Transaction>>
    suspend fun saveTransactions(login: String, transactions: List<Transaction>): Operation
}
