package com.agb.lemon_android.frameworks.datasource.remote.api

import com.agb.lemon_android.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private fun getLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private inline fun <reified T> getApi(client: OkHttpClient) = Retrofit.Builder()
    .baseUrl(BuildConfig.API_BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(T::class.java)

fun getAuthService(): LemonAuthApi = getApi<LemonAuthApi>(
    OkHttpClient.Builder().apply {
        addInterceptor(getLoggingInterceptor())
    }.build()
)

fun getApiService(credentials: String): LemonApi = getApi<LemonApi>(
    OkHttpClient.Builder().apply {
        addInterceptor(BasicAuthInterceptor(credentials))
        addInterceptor(getLoggingInterceptor())
    }.build()
)
