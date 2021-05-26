package com.agb.data.remote.api

import com.agb.core.domain.model.User
import retrofit2.http.GET
import retrofit2.http.Header

interface AuthApi {

    @GET("/profile/")
    suspend fun login(@Header("Authorization") credentials: String): User
}
