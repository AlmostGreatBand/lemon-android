package com.agb.data.di

import com.agb.core.datasource.AuthDataSource
import com.agb.core.datasource.UserDataSource
import com.agb.core.di.local
import com.agb.core.di.remote
import com.agb.core.domain.interactor.UserInteractor
import com.agb.core.domain.repository.UserRepository
import com.agb.core.domain.repository.UserRepositoryImpl
import com.agb.data.local.SecuredPreferencesManager
import com.agb.data.local.UserLocalDataSource
import com.agb.data.remote.AuthRemoteDataSource
import com.agb.data.remote.UserRemoteDataSource
import com.agb.data.remote.api.AuthApi
import com.agb.data.remote.api.UserApi
import com.agb.data.remote.getApiService
import com.agb.data.remote.getAuthService
import org.koin.dsl.module

val dataModule = module {
    single { SecuredPreferencesManager(get()) }
    single<UserDataSource>(local) { UserLocalDataSource(get()) }

    single { getApiService<UserApi>(get(), get(local)) }
    single<UserDataSource>(remote) { UserRemoteDataSource(get()) }

    single { getAuthService<AuthApi>(get()) }
    single<AuthDataSource> { AuthRemoteDataSource(get()) }

    single<UserRepository> { UserRepositoryImpl(get(local), get(remote), get()) }

    single { UserInteractor(get()) }
}
