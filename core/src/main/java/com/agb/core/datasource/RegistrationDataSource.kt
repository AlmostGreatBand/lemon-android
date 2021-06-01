package com.agb.core.datasource

import com.agb.core.common.Operation
import com.agb.core.domain.model.User

interface RegistrationDataSource {
    suspend fun register(user: User): Operation
}
