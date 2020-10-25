package com.agb.core.domain.model

data class Card(
    val cardId: Int,
    val bank: String,
    val cardNum: Int,
    val type: String,
    val balance: Int,
    val currency: String,
)
