package com.agb.lemon_android.frameworks.datasource.remote.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LemonClient {
    fun getApiService(credentials: String): LemonApi {
        val client = OkHttpClient.Builder().apply {
            addInterceptor(BasicAuthInterceptor(credentials))
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("localhost:8080")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(LemonApi::class.java)
    }

    fun getAuthService(): LemonAuthApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("localhost:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(LemonAuthApi::class.java)
    }
}
