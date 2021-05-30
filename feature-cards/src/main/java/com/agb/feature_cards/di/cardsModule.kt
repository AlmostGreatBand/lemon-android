package com.agb.feature_cards.di

import com.agb.core.di.local
import com.agb.core.di.remote
import com.agb.data.remote.getApiService
import com.agb.feature_cards.core.datasource.CardsDataSource
import com.agb.feature_cards.data.remote.CardsApi
import com.agb.feature_cards.data.remote.CardsRemoteDataSource
import org.koin.dsl.module

val cardsModule = module {
    single { getApiService<CardsApi>(get(), get(local)) }

    single<CardsDataSource>(remote) { CardsRemoteDataSource(get()) }
}
