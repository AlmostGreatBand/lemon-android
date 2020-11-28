package com.agb.lemon_android.frameworks.datasource.remote.api

import com.agb.core.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LemonAuthApi {
    @POST("registration")
    suspend fun registration(@Body user: User)

    @GET("profile")
    suspend fun login(@Header("Authorization") credentials: String): User
}
