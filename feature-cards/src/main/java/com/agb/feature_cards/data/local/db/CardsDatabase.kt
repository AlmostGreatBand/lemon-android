package com.agb.feature_cards.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CardsEntity::class], version = 1, exportSchema = false)
abstract class CardsDatabase : RoomDatabase() {
    abstract fun cardsDao(): CardsDao
}
