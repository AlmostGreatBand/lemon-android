package com.agb.data

import com.agb.core.common.Result
import com.agb.core.domain.model.User
import com.agb.data.remote.AuthRemoteDataSource
import com.agb.data.remote.api.AuthApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class AuthRemoteDataSourceTest {
    private val api by lazy { Mockito.mock(AuthApi::class.java) }
    private val ds by lazy { AuthRemoteDataSource(api) }

    private inline fun <reified T> any(): T = Mockito.any(T::class.java)

    @Test
    fun `test login user`() = runBlocking {
        val user = User("name", "login", "pass")
        Mockito.`when`(api.login(any())).thenReturn(user)

        val res = ds.loginUser(user.login, user.password)

        if (res !is Result.Success) {
            Assert.fail("Can't login")
            return@runBlocking
        }

        Assert.assertEquals("Must return user from api", user, res.data)

        Mockito.`when`(api.login(any())).then { error("") }

        if (ds.loginUser(user.login, user.password) !is Result.Error) {
            Assert.fail("Must catch error from api")
        }

        return@runBlocking
    }
}
