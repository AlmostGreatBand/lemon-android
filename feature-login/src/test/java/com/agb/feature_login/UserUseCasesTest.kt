// package com.agb.feature_login
//
// import com.agb.core.common.Result
// import com.agb.core.common.exceptions.LogicError
// import com.agb.core.domain.model.User
// import com.agb.feature_login.core.interactor.UserUseCase
// import com.agb.feature_login.core.repository.UserRepository
// import kotlinx.coroutines.runBlocking
// import org.junit.Assert
// import org.junit.Before
// import org.junit.Test
// import org.mockito.Mockito
// import org.mockito.Mockito.times
//
// class UserUseCasesTest {
//    private val repo by lazy { Mockito.mock(UserRepository::class.java) }
//    private var hegel = User(
//        name = "Georg Hegel",
//        login = "dialecticman",
//        password = "endOfHistory"
//    )
//
//    private inline fun <reified T> any(): T = Mockito.any(T::class.java)
//
//    @Before
//    fun init() = runBlocking {
//        Mockito.`when`(repo::currentUser.get()).thenReturn(hegel)
//        Mockito.`when`(repo.login(any(), any())).thenReturn(Result.Error(LogicError.UserNotExists))
//        Mockito.`when`(repo.login(hegel.login, hegel.password)).thenReturn(Result.Ok)
//        Mockito.`when`(repo.saveUserInfo(any())).thenReturn(Result.Ok)
//
//        return@runBlocking
//    }
//
//    @Test
//    fun `test login use case`() = runBlocking {
//        val useCase = UserUseCase.Login(repo)
//
//        useCase(hegel.login, hegel.password).fold(
//            success = {
//                Mockito.verify(repo, times(1)).login(hegel.login, hegel.password)
//            },
//            failure = {
//                Assert.fail("Must login successfully, $it")
//            },
//            pending = {
//                Assert.fail("Must login successfully, pending")
//            }
//        )
//
//        useCase("test", "test").fold(
//            success = { Assert.fail("Repository returns error, but use case returns ok") },
//            failure = {},
//            pending = {
//                Assert.fail("Repository returns error, but use case returns pending")
//            }
//        )
//
//        return@runBlocking
//    }
//
//    @Test
//    fun `test get user use case`() {
//        val useCase = UserUseCase.GetUser(repo)
//        Assert.assertEquals("Must obtain current user from repository", hegel, useCase())
//    }
//
//    @Test
//    fun `test save user use case`() = runBlocking {
//        val useCase = UserUseCase.SaveUser(repo)
//        useCase(User("test", "test", "test")).fold(
//            success = { Mockito.verify(repo, times(1)).saveUserInfo(any()) },
//            pending = { Assert.fail("Must save user but returns pending") },
//            failure = { Assert.fail("Must save user but returns error, $it") }
//        )
//
//        Mockito.`when`(repo.saveUserInfo(any())).thenReturn(Result.Error(""))
//        useCase(User("test", "test", "test")).fold(
//            success = { Assert.fail("Repository returns Error but use cases returns Ok") },
//            pending = { Assert.fail("Repository returns Error but use cases returns Pending") },
//            failure = {}
//        )
//
//        return@runBlocking
//    }
//
//    @Test
//    fun `test logout`() {
//        val useCase = UserUseCase.Logout(repo)
//        useCase()
//        Mockito.verify(repo, times(1)).clear()
//    }
// }
