package com.agb.feature_home.ui.transactions

import com.agb.feature_transactions.core.domain.models.Transaction
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

open class TransactionRecyclerItem(
    val title: String,
    val type: Type = Type.Transaction,
    val icon: Int? = null,
    val description: String? = null,
    val amount: Int? = null,
    val onClick: (TransactionRecyclerItem.() -> Unit)? = null
) {
    enum class Type {
        Date, Transaction
    }
}

class TransactionHeader(
    title: String,
) : TransactionRecyclerItem(title, Type.Date)

fun List<Transaction>.toTransactionRecyclerItems() = sortedByDescending { it.date }
    .groupBy { it.date.toShortDate() }
    .flatMap { (date, transactions) ->
        listOf(TransactionHeader(title = date)) + transactions.map {
            TransactionRecyclerItem(
                title = it.type,
                amount = it.amount,
            )
        }
    }

private fun Date.toShortDate(now: Date = Date()): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    val year = calendar.get(Calendar.YEAR)
    val day = calendar.get(Calendar.DAY_OF_YEAR)

    calendar.time = now
    val currentYear = calendar.get(Calendar.YEAR)
    val currentDay = calendar.get(Calendar.DAY_OF_YEAR)

    return when {
        abs(currentYear - year) > 0 -> formDate("d MMM yyyy", this)
        abs(currentDay - day) == 0 -> "Today"
        abs(currentDay - day) == 1 -> "Yesterday"
        else -> formDate("d MMM", this)
    }
}

private fun formDate(pattern: String, date: Date, locale: Locale = Locale.getDefault()): String {
    return SimpleDateFormat(pattern, locale).format(date).toLowerCase(locale)
}
