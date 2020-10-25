package com.agb.core.domain.model

import java.util.Date

data class Transaction(
    val cardId: Int,
    val amount: Int,
    val type: String,
    val date: Date,
)
