package com.agb.feature_transactions.core.domain.repository

import com.agb.core.common.orElse
import com.agb.feature_transactions.core.datasource.TransactionsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionsRepository(
    private val local: TransactionsDataSource,
    private val remote: TransactionsDataSource,
) {
    suspend fun getTransactions(login: String) = withContext(Dispatchers.IO) {
        remote.getTransactions(login)
            .onSuccess { transactions -> local.saveTransactions(login, transactions) }
            .orElse { local.getTransactions(login) }
    }
}
