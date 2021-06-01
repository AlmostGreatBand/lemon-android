package com.agb.data.remote.api

import com.agb.core.domain.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationApi {
    @POST("/register/")
    suspend fun register(@Body user: User)
}
