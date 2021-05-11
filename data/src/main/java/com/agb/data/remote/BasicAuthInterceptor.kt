package com.agb.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(private val credentials: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            header("Authorization", credentials)
            header("Content-Type", "application/json")
        }.build()

        return chain.proceed(request)
    }
}
