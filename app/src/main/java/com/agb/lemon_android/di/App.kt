package com.agb.lemon_android.di

import android.app.Application
import com.agb.data.di.dataModule
import com.agb.feature_home.di.homeModule
import com.agb.feature_login.di.loginModule
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
            modules(loginModule)
            modules(homeModule)
        }
    }
}
