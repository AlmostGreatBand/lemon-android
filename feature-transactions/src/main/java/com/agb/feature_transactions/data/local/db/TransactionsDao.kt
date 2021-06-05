package com.agb.feature_transactions.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionsDao {
    @Query("select * from transactions where user = :login")
    fun getTransactions(login: String): List<TransactionsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(transactions: List<TransactionsEntity>)
}
