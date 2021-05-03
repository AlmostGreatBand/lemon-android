package com.agb.core

import com.agb.core.common.Result
import com.agb.core.common.exceptions.UnexpectedLemonException
import org.junit.Assert
import org.junit.Test

class ResultTest {
    @Test
    fun `test onSuccess`() {
        val res = Result.Success(1)
        var called = false
        val res2 = res.onSuccess {
            called = true
            Assert.assertEquals(1, it)
        }

        if (!called) Assert.fail("Must call callback immediately")
        Assert.assertSame("Must return same object", res, res2)

        Result.Pending.onSuccess { Assert.fail("Must not call onSuccess on Result.Pending") }
        Result.Error(UnexpectedLemonException("")).onSuccess {
            Assert.fail("Must not call onError on Result.Success")
        }
    }

    @Test
    fun `test onError`() {
        val err = UnexpectedLemonException("Something wrong!")
        val res = Result.Error(err)
        var called = false
        val res2 = res.onError {
            called = true
            Assert.assertSame(err, it)
        }

        if (!called) Assert.fail("Must call callback immediately")
        Assert.assertSame("Must return same object", res, res2)

        Result.Success(1).onError { Assert.fail("Must not call onError on Result.Success") }
        Result.Pending.onError { Assert.fail("Must not call onError on Result.Pending") }
    }

    @Test
    fun `test onPending`() {
        val res = Result.Pending
        var called = false
        val res2 = res.onPending {
            called = true
        }

        if (!called) Assert.fail("Must call callback immediately")
        Assert.assertSame("Must return same object", res, res2)

        Result.Success(1).onPending { Assert.fail("Must not call onPending on Result.Success") }
        Result.Error(UnexpectedLemonException("")).onPending {
            Assert.fail("Must not call onPending on Result.Error")
        }
    }

    @Test
    fun `test fold`() {
        var cbCalled = false

        val success = Result.Success(1)
        val res1 = success.fold(
            success = {
                cbCalled = true
                Assert.assertEquals(it, 1)
            },
            pending = { Assert.fail("Must not call pending cb on Success") },
            failure = { Assert.fail("Must not call failure cb on Success") }
        )

        Assert.assertTrue("Must call success cb on Success", cbCalled)
        Assert.assertSame("Must return same object", success, res1)

        cbCalled = false
        val res2 = Result.Pending.fold(
            success = { Assert.fail("Must not call success cb on Pending") },
            pending = {
                cbCalled = true
            },
            failure = { Assert.fail("Must not call failure cb on Pending") }
        )

        Assert.assertTrue("Must call pending cb on Pending", cbCalled)
        Assert.assertSame("Must return same object", Result.Pending, res2)

        cbCalled = false
        val exception = UnexpectedLemonException("")
        val error = Result.Error(exception)
        val res3 = error.fold(
            success = { Assert.fail("Must not call success cb on Error") },
            pending = { Assert.fail("Must not call pending cb on Error") },
            failure = {
                cbCalled = true
                Assert.assertEquals(exception, it)
            }
        )

        Assert.assertTrue("Must call failure cb on Error", cbCalled)
        Assert.assertSame("Must return same object", error, res3)
    }

    @Test
    fun `test map`() {
        val success = Result.Success(1)
        val r1 = success.map {
            Assert.assertEquals(it, 1)
            2
        }

        if (r1 !is Result.Success || r1.data != 2) {
            Assert.fail("Must return new result with changed data")
        }

        val error = Result.Error(UnexpectedLemonException(""))
        val r2 = error.map { Assert.fail("Must not call map on Error") }
        Assert.assertSame("Must return same Error object", error, r2)

        val r3 = Result.Pending.map { Assert.fail("Must not call map on Pending") }
        Assert.assertSame("Must return same Pending object", Result.Pending, r3)
    }

    @Test
    fun `test then`() {
        val error = Result.Error(UnexpectedLemonException(""))
        val r1 = error.then<Nothing> {
            Assert.fail("Must not call then on Error")
            error("Must not call then on Error")
        }
        Assert.assertSame("Must return same Error object", error, r1)

        val r2 = Result.Pending.then<Nothing> {
            Assert.fail("Must not call then on Pending")
            error("Must not call then on Pending")
        }
        Assert.assertSame("Must return same Pending object", Result.Pending, r2)

        val s1 = Result.Success(1)
        val s2 = Result.Success(2)

        val r3 = s1.then {
            Assert.assertEquals(it, 1)
            s2
        }
        Assert.assertSame("Must return new Success object", s2, r3)

        val e1 = Result.Error(UnexpectedLemonException(""))
        val r4 = s1.then { e1 }
        Assert.assertSame("Must return new Error object", e1, r4)

        val r5 = s1.then { Result.Pending }
        Assert.assertSame("Must return Pending object", Result.Pending, r5)
    }
}
