package com.agb.feature_cards.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardsDao {
    @Query("select * from cards where user = :login")
    fun getCards(login: String): List<CardsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cards: List<CardsEntity>)
}
