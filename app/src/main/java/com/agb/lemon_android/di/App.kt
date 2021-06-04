package com.agb.lemon_android.di

import android.app.Application
import com.agb.data.di.dataModule
import com.agb.feature_cards.di.cardsModule
import com.agb.feature_home.di.homeModule
import com.agb.feature_login.di.loginModule
import com.agb.feature_profile.di.profileModule
import com.agb.feature_registration.di.registrationModule
import com.agb.feature_transactions.di.transactionsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            androidLogger(Level.DEBUG)

            // app
            modules(appModule)
            modules(dataModule)

            // features
            modules(transactionsModule)
            modules(cardsModule)
            modules(loginModule)
            modules(homeModule)
            modules(profileModule)
            modules(registrationModule)
        }
    }
}
