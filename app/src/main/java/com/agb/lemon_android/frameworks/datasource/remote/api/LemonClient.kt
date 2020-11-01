package com.agb.lemon_android.frameworks.datasource.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LemonClient {
    private lateinit var apiService: LemonApi

    fun getApiService(): LemonApi {
        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl("localhost:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiService = retrofit.create(LemonApi::class.java)
        }

        return apiService
    }
}