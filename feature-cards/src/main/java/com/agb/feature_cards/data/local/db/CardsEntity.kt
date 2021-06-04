package com.agb.feature_cards.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agb.feature_cards.core.domain.models.Card

@Entity(tableName = "cards")
data class CardsEntity(
    @PrimaryKey
    @ColumnInfo(name = "card_id")
    val cardId: Int,
    val user: String,
    val bank: String,
    @ColumnInfo(name = "card_num")
    val cardNum: Int,
    val type: String,
    val balance: Int,
    val currency: String,
)

fun Card.toCardsEntity(login: String) = CardsEntity(
    cardId = cardId,
    type = type,
    user = login,
    cardNum = cardNum,
    balance = balance,
    bank = bank,
    currency = currency,
)

fun CardsEntity.toCards() = Card(
    cardId = cardId,
    type = type,
    cardNum = cardNum,
    balance = balance,
    bank = bank,
    currency = currency,
)
