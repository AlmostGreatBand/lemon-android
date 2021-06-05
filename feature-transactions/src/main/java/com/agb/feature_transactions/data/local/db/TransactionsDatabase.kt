package com.agb.feature_transactions.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TransactionsEntity::class], version = 1, exportSchema = false)
@TypeConverters(TransactionConverters::class)
abstract class TransactionsDatabase : RoomDatabase() {
    abstract fun transactionsDao(): TransactionsDao
}
