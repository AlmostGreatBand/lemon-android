package com.agb.feature_transactions.di

import androidx.room.Room
import com.agb.core.di.local
import com.agb.core.di.remote
import com.agb.data.remote.getApiService
import com.agb.feature_transactions.core.datasource.TransactionsDataSource
import com.agb.feature_transactions.core.domain.repository.TransactionsRepository
import com.agb.feature_transactions.data.local.TransactionsLocalDataSource
import com.agb.feature_transactions.data.local.db.TransactionsDatabase
import com.agb.feature_transactions.data.remote.TransactionsApi
import com.agb.feature_transactions.data.remote.TransactionsRemoteDataSource
import org.koin.dsl.module

val transactionsModule = module {
    single { getApiService<TransactionsApi>(get(), get(local)) }
    single {
        Room.databaseBuilder(
            get(),
            TransactionsDatabase::class.java,
            "transactions-db",
        ).build().transactionsDao()
    }

    single<TransactionsDataSource>(remote) { TransactionsRemoteDataSource(get()) }
    single<TransactionsDataSource>(local) { TransactionsLocalDataSource(get()) }

    single { TransactionsRepository(get(local), get(remote)) }
}
