package com.agb.data.remote.api

import com.agb.core.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @GET("/profile/")
    suspend fun profile(): User

    @POST("/profile/")
    suspend fun updateProfile(@Body user: User)
}
