package com.agb.feature_transactions.data.remote

import com.agb.core.common.Result
import com.agb.core.common.exceptions.UnexpectedLemonException
import com.agb.data.remote.HttpExceptionHandler
import com.agb.feature_transactions.core.datasource.TransactionsDataSource
import com.agb.feature_transactions.core.domain.models.Transaction
import retrofit2.HttpException

class TransactionsRemoteDataSource(
    private val api: TransactionsApi,
) : TransactionsDataSource, HttpExceptionHandler {
    override suspend fun getTransactions(login: String): Result<List<Transaction>> {
        return try {
            Result.Success(api.getTransactions().transactions)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> Result.Error(handleHttpException(e))
                else -> Result.Error(UnexpectedLemonException(e))
            }
        }
    }

    override suspend fun saveTransactions(
        login: String,
        transactions: List<Transaction>,
    ) = Result.Error("Stub!")
}
