package com.jmarkstar.sampletest.repository.network

import android.os.Build
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class UserServiceTest: BaseNetworkTest() {

    private val userService: UserService by inject()

    @Test
    fun `get all users success`() = runBlocking {

        val getUsersResult = userService.getUsers()

        Assert.assertEquals(true, getUsersResult.code() == 200)
        Assert.assertNotNull(getUsersResult.body())
        Assert.assertEquals(true, getUsersResult.body()!!.isNotEmpty())
    }
}