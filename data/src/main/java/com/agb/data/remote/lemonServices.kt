package com.agb.data.remote

import android.content.Context
import com.agb.data.BuildConfig
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun getChuckerInterceptor(context: Context): ChuckerInterceptor {
    val chuckerCollector = ChuckerCollector(
        context = context,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

    return ChuckerInterceptor.Builder(context)
        .collector(chuckerCollector)
        .alwaysReadResponseBody(true)
        .build()
}

inline fun <reified T> getApi(client: OkHttpClient): T = Retrofit.Builder()
    .baseUrl(BuildConfig.API_BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(T::class.java)

inline fun <reified T> getAuthService(context: Context): T = getApi(
    OkHttpClient.Builder()
        .addInterceptor(getLoggingInterceptor())
        .addInterceptor(getChuckerInterceptor(context))
        .build()
)

inline fun <reified T> getApiService(context: Context, credentials: String): T = getApi(
    OkHttpClient.Builder().apply {
        addInterceptor(BasicAuthInterceptor(credentials))
        addInterceptor(getLoggingInterceptor())
        addInterceptor(getChuckerInterceptor(context))
    }.build()
)
