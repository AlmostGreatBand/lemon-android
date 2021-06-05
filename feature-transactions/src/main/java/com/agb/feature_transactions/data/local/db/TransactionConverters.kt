package com.agb.feature_transactions.data.local.db

import androidx.room.TypeConverter
import java.util.Date

class TransactionConverters {
    @TypeConverter
    fun toDate(millis: Long) = Date(millis)

    @TypeConverter
    fun fromDate(date: Date) = date.time
}
