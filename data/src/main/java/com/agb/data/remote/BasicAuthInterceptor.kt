package com.agb.data.remote

import com.agb.core.datasource.UserDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(private val userDataSource: UserDataSource) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            header("Content-Type", "application/json")
        }

        runBlocking {
            userDataSource.getUserInfo()
                .onSuccess {
                    request.header("Authorization", Credentials.basic(it.login, it.password))
                }
                .onError {
                    error("Not authorized")
                }
        }

        return chain.proceed(request.build())
    }
}
