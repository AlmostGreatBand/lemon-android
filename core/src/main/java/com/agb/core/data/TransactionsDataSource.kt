package com.agb.core.data

import com.agb.core.common.Result
import com.agb.core.domain.model.Transaction

interface TransactionsDataSource {
    suspend fun getTransactions(): Result<List<Transaction>>
    suspend fun saveTransactions(data: List<Transaction>): Result<Unit>
}