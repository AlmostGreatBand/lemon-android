package com.agb.data

import com.agb.core.common.Result
import com.agb.core.domain.model.User
import com.agb.data.local.SecuredPreferencesManager
import com.agb.data.local.UserLocalDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times

class UserLocalDataSourceTest {
    private val preferences by lazy { Mockito.mock(SecuredPreferencesManager::class.java) }
    private val ds by lazy { UserLocalDataSource(preferences) }

    private inline fun <reified T> any(): T = Mockito.any(T::class.java)

    private var prefs = mutableMapOf<String, String>()

    @Before
    fun init() {
        Mockito.`when`(preferences.set(any(), any())).then {
            val k = it.arguments[0] as String
            val v = it.arguments[1] as String

            prefs[k] = v
            Unit
        }

        Mockito.`when`(preferences[any()]).then {
            val k = it.arguments[0] as String
            prefs[k]
        }
        Mockito.`when`(preferences.clear()).then {
            prefs.clear()
            Unit
        }
    }

    @Test
    fun `save user test`() = runBlocking {
        val fidel = User(
            name = "Fidel Castro",
            login = "fidel",
            password = "cuba_libre"
        )

        val res = ds.saveUserInfo(fidel)

        if (res !is Result.Success) Assert.fail("Can't save user: $res")

        val name = prefs.values.find { it == fidel.name }
        val loginPass = prefs.values.find { it == "ZmlkZWw6Y3ViYV9saWJyZQ==" }

        Assert.assertNotNull("Must save user's name", name)
        Assert.assertNotNull("Must save login and password!", loginPass)

        Mockito.`when`(preferences.set(any(), any())).then { error("") }

        val res2 = ds.saveUserInfo(fidel)
        if (res2 !is Result.Error) {
            Assert.fail("Must catch error and return Result.Error")
        }
    }

    @Test
    fun `get user info test`() = runBlocking {
        val fidel = User(
            name = "Fidel Castro",
            login = "fidel",
            password = "cuba_libre"
        )

        ds.saveUserInfo(fidel).onError {
            Assert.fail("Can't save user info")
        }

        val res = ds.getUserInfo()

        if (res !is Result.Success) {
            Assert.fail("Can't decode login & password")
            return@runBlocking
        }

        val user = res.data

        Assert.assertEquals("Must decode login", fidel.login, user.login)
        Assert.assertEquals("Must decode password", fidel.password, user.password)
        Assert.assertEquals("Must decode password", fidel.name, user.name)

        return@runBlocking
    }

    @Test
    fun `delete user info`() = runBlocking {
        val res = ds.deleteUserInfo()

        if (res !is Result.Success) {
            Assert.fail("Can't clear user info")
        }

        Mockito.verify(preferences, times(1)).clear()

        Mockito.`when`(preferences.clear()).then { error("") }

        if (ds.deleteUserInfo() !is Result.Error) {
            Assert.fail("Preferences throwed error but DS didn't catch it")
        }

        return@runBlocking
    }
}
