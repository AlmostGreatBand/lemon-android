package com.agb.data.local

import com.agb.core.common.Result
import com.agb.core.common.exceptions.LogicError
import com.agb.core.common.exceptions.UnexpectedLemonException
import com.agb.core.datasource.UserDataSource
import com.agb.core.domain.model.User
import java.lang.Exception
import java.nio.charset.Charset
import okio.ByteString

class UserLocalDataSource(
    private val preferencesManager: SecuredPreferencesManager
) : UserDataSource {
    private val PREF_CREDS = "PREF_CREDS"
    private val PREF_USERNAME = "PREF_USERNAME"

    override suspend fun getUserInfo(): Result<User> {
        val creds = preferencesManager[PREF_CREDS] ?: return Result.Error(LogicError.UserNotExists)
        val username = preferencesManager[PREF_USERNAME] ?: ""

        val splitted = ByteString.decodeBase64(creds)?.utf8()?.split(':')

        if (splitted.isNullOrEmpty()) {
            return Result.Error(LogicError.UserNotExists)
        }

        val (login, pass) = splitted

        if (login.isBlank() || pass.isBlank()) {
            preferencesManager.clear()
            return Result.Error(LogicError.UserNotExists)
        }

        return Result.Success(User(username, login, pass))
    }

    override suspend fun saveUserInfo(user: User): Result<Unit> {
        return try {
            val (name, login, pass) = user
            preferencesManager[PREF_USERNAME] = name
            preferencesManager[PREF_CREDS] = ByteString
                .encodeString("$login:$pass", Charset.defaultCharset())
                .base64()

            Result.Ok
        } catch (e: Exception) {
            Result.Error(UnexpectedLemonException(e))
        }
    }
}
