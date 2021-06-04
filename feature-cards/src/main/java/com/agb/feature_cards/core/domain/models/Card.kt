package com.agb.feature_cards.core.domain.models

import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("card_id")
    val cardId: Int,
    val bank: String,
    @SerializedName("card_num")
    val cardNum: Int,
    val type: String,
    val balance: Int,
    val currency: String,
)
