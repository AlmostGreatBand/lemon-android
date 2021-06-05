package com.agb.feature_registration

import com.agb.core.common.Result
import com.agb.core.domain.interactor.UserInteractor
import com.agb.feature_registration.ui.RegistrationViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times

@ExperimentalCoroutinesApi
class RegistrationViewModelTest {
    private val scope = TestCoroutineScope()
    private val interactor by lazy { Mockito.mock(UserInteractor::class.java) }
    private val vm by lazy { RegistrationViewModel(interactor, scope) }

    private inline fun <reified T> any(): T = Mockito.any()

    @Before
    fun init() = runBlockingTest {
        Mockito.`when`(interactor.register(any())).thenReturn(Result.Ok)
    }

    @After
    fun finish() {
        scope.cleanupTestCoroutines()
    }

    @Test
    fun `test validation`() = runBlockingTest {
        Assert.assertFalse(vm.isDataValid.value)

        vm.login.value = "test"
        vm.password.value = ""
        vm.username.value = ""

        Assert.assertFalse(vm.isDataValid.value)

        vm.login.value = "test"
        vm.password.value = "test"
        vm.username.value = ""

        Assert.assertFalse(vm.isDataValid.value)

        vm.login.value = "test"
        vm.password.value = "test"
        vm.username.value = "test"

        Assert.assertTrue(vm.isDataValid.value)

        vm.login.value = "test"
        vm.password.value = ""
        vm.username.value = "test"

        Assert.assertFalse(vm.isDataValid.value)

        vm.login.value = ""
        vm.password.value = ""
        vm.username.value = "test"

        Assert.assertFalse(vm.isDataValid.value)

        vm.login.value = "     "
        vm.password.value = "  1"
        vm.username.value = "test"

        Assert.assertFalse(vm.isDataValid.value)
    }

    @Test
    fun `test register`() = runBlockingTest {
        Assert.assertSame(null, vm.status.value)

        vm.login.value = "test"
        vm.password.value = "test"
        vm.username.value = "test"

        var collectedPending = false

        val j = launch {
            vm.status.collect {
                if (it is Result.Pending) {
                    collectedPending = true
                    return@collect
                }
            }
        }

        vm.register()

        Assert.assertSame(Result.Ok, vm.status.value)
        Assert.assertTrue(collectedPending)
        Mockito.verify(interactor, times(1)).register(any())

        Mockito.`when`(interactor.register(any())).thenReturn(Result.Error(""))

        j.cancel()
        vm.register()

        val status = vm.status.value

        Assert.assertTrue(
            "Interactor returned error, but ViewModel returns $status",
            status is Result.Error
        )
    }
}
