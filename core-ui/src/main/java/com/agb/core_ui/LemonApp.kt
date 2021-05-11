package com.agb.core_ui

import android.app.Application

abstract class LemonApp : Application(), AppModuleProvider {
    private var _appModule: AppModule? = null
    override val appModule: AppModule get() = _appModule!!

    override fun onCreate() {
        super.onCreate()

        _appModule = AppModule(this)
    }
}
