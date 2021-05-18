package com.agb.feature_home.di

import com.agb.feature_home.ui.HomeViewModel
import com.agb.feature_login.di.AuthModule
import com.agb.feature_login.di.LoginModule
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Component(
    modules = [
        LoginModule::class,
        AuthModule::class,
    ]
)
@Singleton
interface HomeComponent {
    fun inject(homeViewModel: HomeViewModel)
}

@Module
interface HomeModule
