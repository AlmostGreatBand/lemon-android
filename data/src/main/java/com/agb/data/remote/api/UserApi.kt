package com.agb.data.remote.api

import com.agb.core.domain.model.User
import retrofit2.http.GET

interface UserApi {

    @GET("/profile/")
    suspend fun profile(): User
}
