package com.agb.data

import com.agb.core.common.Result
import com.agb.core.domain.model.User
import com.agb.data.remote.UserRemoteDataSource
import com.agb.data.remote.api.UserApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times

class UserRemoteDataSourceTest {
    private val api by lazy { Mockito.mock(UserApi::class.java) }
    private val ds by lazy { UserRemoteDataSource(api) }
    private val user = User("name", "login", "password")

    private inline fun <reified T> any(): T = Mockito.any(T::class.java)

    @Before
    fun init() = runBlocking {
        Mockito.`when`(api.profile()).thenReturn(user)
        Mockito.`when`(api.updateProfile(any())).thenReturn(Unit)
        return@runBlocking
    }

    @Test
    fun `get user info test`() = runBlocking {
        val res = ds.getUserInfo()

        if (res !is Result.Success) {
            Assert.fail("Cannot obtain user data")
            return@runBlocking
        }

        Assert.assertEquals("Must return user from api", user, res.data)

        Mockito.`when`(api.profile()).then { error("") }
        if (ds.getUserInfo() !is Result.Error) {
            Assert.fail("Must catch error from api")
        }

        return@runBlocking
    }

    @Test
    fun `save user info test`() = runBlocking {
        if (ds.saveUserInfo(user) !is Result.Success) {
            Assert.fail("Can't save user info")
        }

        Mockito.verify(api, times(1)).updateProfile(any())

        Mockito.`when`(api.updateProfile(any())).then { error("") }

        if (ds.saveUserInfo(user) !is Result.Error) {
            Assert.fail("Must catch error from api")
        }

        return@runBlocking
    }
}
