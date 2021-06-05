package com.agb.feature_transactions.data.local

import android.util.Log
import com.agb.core.common.Result
import com.agb.core.common.exceptions.UnexpectedLemonException
import com.agb.feature_transactions.core.datasource.TransactionsDataSource
import com.agb.feature_transactions.core.domain.models.Transaction
import com.agb.feature_transactions.data.local.db.TransactionsDao
import com.agb.feature_transactions.data.local.db.TransactionsEntity
import com.agb.feature_transactions.data.local.db.toTransaction
import com.agb.feature_transactions.data.local.db.toTransactionEntity

class TransactionsLocalDataSource(
    private val transactionsDao: TransactionsDao,
) : TransactionsDataSource {
    private val tag = this::class.java.simpleName

    override suspend fun getTransactions(login: String) = try {
        val transactionList = transactionsDao.getTransactions(login)
        if (transactionList.isNotEmpty()) {
            val mapped = transactionList.map(TransactionsEntity::toTransaction)
            Result.Success(mapped)
        } else {
            Result.Error(UnexpectedLemonException("No transactions cached for user $login"))
        }
    } catch (e: Exception) {
        Log.e(tag, "getTransactionsLocal: ${e.message}")
        Result.Error(UnexpectedLemonException("Can't get transactions locally"))
    }

    override suspend fun saveTransactions(login: String, transactions: List<Transaction>) = try {
        val transactionsEntities = transactions.map { tr -> tr.toTransactionEntity(login) }
        transactionsDao.insertAll(transactionsEntities)
        Result.Ok
    } catch (e: Exception) {
        Log.e(tag, "saveTransactionsLocal: ${e.message}")
        Result.Error(UnexpectedLemonException("Can't cache transactions"))
    }
}
