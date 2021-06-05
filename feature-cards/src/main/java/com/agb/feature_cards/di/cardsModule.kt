package com.agb.feature_cards.di

import androidx.room.Room
import com.agb.core.di.local
import com.agb.core.di.remote
import com.agb.data.remote.getApiService
import com.agb.feature_cards.core.datasource.CardsDataSource
import com.agb.feature_cards.core.domain.repository.CardsRepository
import com.agb.feature_cards.data.local.CardsLocalDataSource
import com.agb.feature_cards.data.local.db.CardsDatabase
import com.agb.feature_cards.data.remote.CardsApi
import com.agb.feature_cards.data.remote.CardsRemoteDataSource
import org.koin.dsl.module

val cardsModule = module {
    single { getApiService<CardsApi>(get(), get(local)) }
    single {
        Room.databaseBuilder(
            get(),
            CardsDatabase::class.java,
            "cards-db",
        ).build().cardsDao()
    }

    single<CardsDataSource>(remote) { CardsRemoteDataSource(get()) }
    single<CardsDataSource>(local) { CardsLocalDataSource(get()) }

    single { CardsRepository(get(local), get(remote)) }
}
