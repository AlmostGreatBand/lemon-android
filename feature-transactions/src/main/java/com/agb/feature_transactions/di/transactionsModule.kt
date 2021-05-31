package com.agb.feature_transactions.di

import com.agb.core.di.local
import com.agb.core.di.remote
import com.agb.data.remote.getApiService
import com.agb.feature_transactions.core.datasource.TransactionsDataSource
import com.agb.feature_transactions.data.remote.TransactionsApi
import com.agb.feature_transactions.data.remote.TransactionsRemoteDataSource
import org.koin.dsl.module

val transactionsModule = module {
    single { getApiService<TransactionsApi>(get(), get(local)) }

    single<TransactionsDataSource>(remote) { TransactionsRemoteDataSource(get()) }
}
