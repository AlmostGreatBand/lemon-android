package com.agb.feature_login.di

import android.content.Context
import com.agb.core.di.Local
import com.agb.core.di.Remote
import com.agb.core_ui.AppModule
import com.agb.data.local.SecuredPreferencesManager
import com.agb.data.remote.getAuthService
import com.agb.feature_login.core.datasource.AuthDataSource
import com.agb.feature_login.core.datasource.UserDataSource
import com.agb.feature_login.core.repository.UserRepository
import com.agb.feature_login.core.repository.UserRepositoryImpl
import com.agb.feature_login.data.local.UserLocalDataSource
import com.agb.feature_login.data.remote.AuthRemoteDataSource
import com.agb.feature_login.data.remote.UserRemoteDataSource
import com.agb.feature_login.data.remote.api.UserApi
import com.agb.feature_login.ui.LoginViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Component(
    modules = [
        LoginModule::class,
        AuthModule::class,
        AppModule::class
    ]
)
@Singleton
interface LoginComponent {
    fun inject(loginViewModel: LoginViewModel)
}

@Module
abstract class LoginModule {
    @Binds
    @Local
    abstract fun userLocalDataSource(userLocalDataSource: UserLocalDataSource): UserDataSource

    @Binds
    @Remote
    abstract fun userRemoteDataSource(userRemoteDataSource: UserRemoteDataSource): UserDataSource

    @Binds
    abstract fun authRemoteDataSource(authRemoteDataSource: AuthRemoteDataSource): AuthDataSource

    @Binds
    @Singleton
    abstract fun userRepositoryImpl(userRepositoryImpl: UserRepositoryImpl): UserRepository
}

@Module
class AuthModule(private val context: Context) {
    @Provides
    fun loginApi(): UserApi = getAuthService(context)

    @Provides
    fun securedPrefs(): SecuredPreferencesManager = SecuredPreferencesManager(context)
}
