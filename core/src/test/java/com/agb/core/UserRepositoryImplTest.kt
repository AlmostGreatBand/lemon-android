package com.agb.core

import com.agb.core.common.Result
import com.agb.core.common.exceptions.UnexpectedLemonException
import com.agb.core.datasource.AuthDataSource
import com.agb.core.datasource.RegistrationDataSource
import com.agb.core.datasource.UserDataSource
import com.agb.core.domain.model.User
import com.agb.core.domain.repository.UserRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times

class UserRepositoryImplTest {
    private val local by lazy { Mockito.mock(UserDataSource::class.java) }
    private val remote by lazy { Mockito.mock(UserDataSource::class.java) }
    private val auth by lazy { Mockito.mock(AuthDataSource::class.java) }
    private val reg by lazy { Mockito.mock(RegistrationDataSource::class.java) }

    private val repo by lazy { UserRepositoryImpl(local, remote, auth, reg) }
    private val user by lazy { User("test", "test", "pass") }

    private inline fun <reified T> any(): T = Mockito.any(T::class.java)

    @Before
    fun init() = runBlocking {
        Mockito.`when`(auth.loginUser(user.login, user.password))
            .thenReturn(Result.Success(user))

        Mockito.`when`(remote.saveUserInfo(any()))
            .thenReturn(Result.Ok)

        Mockito.`when`(local.saveUserInfo(any()))
            .thenReturn(Result.Ok)

        Mockito.`when`(local.deleteUserInfo()).thenReturn(Result.Ok)

        return@runBlocking
    }

    @Test
    fun `test login`() = runBlocking {
        repo.login(user.login, user.password)
        Mockito.verify(auth, times(1)).loginUser(user.login, user.password)
        Mockito.verify(local, times(1)).saveUserInfo(any())

        return@runBlocking
    }

    @Test
    fun `test login with errors`() = runBlocking {
        Mockito.`when`(auth.loginUser(user.login, user.password))
            .thenReturn(Result.Error(""))

        repo.login(user.login, user.password).onSuccess {
            Assert.fail("Auth failed but repository returns Ok")
        }

        Mockito.`when`(auth.loginUser(user.login, user.password))
            .thenReturn(Result.Success(user))

        Mockito.`when`(local.saveUserInfo(any()))
            .thenReturn(Result.Error(""))

        repo.login(user.login, user.password).onError {
            Assert.fail("If local DS failed, must not throw error on login")
        }

        return@runBlocking
    }

    @Test
    fun `test save user`() = runBlocking {
        val rosa = User(
            name = "Rosa Luxemburg",
            login = "rluxemburg",
            password = "spartakusbund"
        )
        repo.saveUserInfo(rosa).onError {
            Assert.fail("Unable to save user info: $it")
        }

        Mockito.verify(remote, times(1)).saveUserInfo(rosa)
        Mockito.verify(local, times(1)).saveUserInfo(rosa)

        val karl = User(
            name = "Karl Liebknecht",
            login = "kliebknecht",
            password = "klassenkampf"
        )

        Mockito.`when`(remote.saveUserInfo(karl)).thenReturn(Result.Error(""))
        repo.saveUserInfo(karl).onSuccess {
            Assert.fail("DataSource returns Error but repo returns Ok")
        }
        Mockito.verify(local, times(0)).saveUserInfo(karl)

        Mockito.`when`(remote.saveUserInfo(karl)).thenReturn(Result.Ok)
        Mockito.`when`(local.saveUserInfo(karl)).thenReturn(Result.Error(""))

        repo.saveUserInfo(karl).onError {
            Assert.fail("Must ignore local dataSource error: $it")
        }

        Mockito.verify(local, times(1)).saveUserInfo(karl)

        return@runBlocking
    }

    @Test
    fun `test logout`() = runBlocking {
        val res1 = repo.logout()
        Mockito.verify(local, times(1)).deleteUserInfo()

        if (res1 !is Result.Success) {
            Assert.fail("Failed to save user info")
        }

        Mockito.`when`(local.deleteUserInfo())
            .thenReturn(Result.Error(UnexpectedLemonException("")))

        val res2 = repo.logout()
        Mockito.verify(local, times(2)).deleteUserInfo()

        if (res2 !is Result.Error) {
            Assert.fail("DataSource returns Error but repository returns $res2")
        }

        return@runBlocking
    }
}
