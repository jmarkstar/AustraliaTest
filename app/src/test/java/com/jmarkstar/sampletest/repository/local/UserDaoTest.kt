package com.jmarkstar.sampletest.repository.local

import android.os.Build
import com.jmarkstar.sampletest.photo1
import com.jmarkstar.sampletest.photos
import com.jmarkstar.sampletest.repository.local.daos.UserDao
import com.jmarkstar.sampletest.user1
import com.jmarkstar.sampletest.users
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class UserDaoTest: BaseLocalTest() {

    private val userDao: UserDao by inject()

    @Test
    fun `insert users test success`()= runBlocking {
        val result = userDao.insertAll(users)
        Assert.assertEquals(users.size, result.size)
    }

    @Test
    fun `get count test success`()= runBlocking {
        userDao.insertAll(users)

        val count = userDao.count()
        Assert.assertEquals(users.size, count)
    }

    @Test
    fun `get users test success`() = runBlocking {
        userDao.insertAll(users)

        val usersResult = userDao.getUsers()
        Assert.assertEquals(usersResult.size, users.size)
    }

    @Test
    fun `delete users test success`() = runBlocking {
        userDao.insertAll(users)

        val affectedRows = userDao.deleteAll()
        Assert.assertEquals(affectedRows, users.size)
    }
}