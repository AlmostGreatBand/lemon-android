package com.agb.feature_login.data.remote.api

import com.agb.core.domain.model.User
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {
    @GET("/profile/")
    suspend fun profile(): User

    @GET("/profile/")
    suspend fun login(@Header("Authorization") credentials: String): User
}
