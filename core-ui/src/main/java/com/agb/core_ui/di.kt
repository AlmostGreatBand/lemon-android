package com.agb.core_ui

import android.app.Application
import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun appContext(): Context
}

@Module
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun getAppContext(): Context = application
}

interface AppModuleProvider {
    val appModule: AppModule
}
