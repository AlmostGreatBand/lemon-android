package com.agb.feature_transactions.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agb.feature_transactions.core.domain.models.Transaction
import java.util.Date

@Entity(tableName = "transactions")
data class TransactionsEntity(
    @PrimaryKey
    @ColumnInfo(name = "transaction_id")
    val transactionId: Int,
    @ColumnInfo(name = "card_id")
    val cardId: Int,
    val user: String,
    val amount: Int,
    val type: String,
    val date: Date,
)

fun Transaction.toTransactionEntity(login: String) = TransactionsEntity(
    transactionId = transactionId,
    cardId = cardId,
    user = login,
    amount = amount,
    type = type,
    date = date,
)

fun TransactionsEntity.toTransaction() = Transaction(
    transactionId = transactionId,
    cardId = cardId,
    amount = amount,
    type = type,
    date = date,
)
