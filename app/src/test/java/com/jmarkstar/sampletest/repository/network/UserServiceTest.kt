package com.jmarkstar.sampletest.repository.network

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.koin.test.inject

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