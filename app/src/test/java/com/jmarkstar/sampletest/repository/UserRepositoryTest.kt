package com.jmarkstar.sampletest.repository

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.jmarkstar.sampletest.di.*
import com.jmarkstar.sampletest.incompleteUserIds
import com.jmarkstar.sampletest.repository.local.daos.UserDao
import com.jmarkstar.sampletest.repository.network.UserService
import com.jmarkstar.sampletest.users
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import com.jmarkstar.sampletest.models.User
import com.jmarkstar.sampletest.userIds
import org.junit.After
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class UserRepositoryTest: BaseRepositoryTest() {

    private val userService: UserService = mock(UserService::class.java)

    private val userDao: UserDao = mock(UserDao::class.java)

    private val userRepository: UserRepository by inject()

    @Before fun setup() {
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(listOf(commonModule, networkModule, databaseTestModule, constantModule, repositoryModule, module {
                single { userService }
                single { userDao }
            }))
        }
    }

    @After fun unsetup() {
        stopKoin()
    }

    @Test
    fun `refresh users Test success`() = runBlocking {

        `when`(userDao.count())
            .thenReturn(0)

        val response = mockResponse<List<User>>()

        `when`(response.code())
            .thenReturn(200)

        `when`(response.body())
            .thenReturn(users)

        `when`(userService.getUsers())
            .thenReturn(response)

        `when`(userDao.insertAll(users))
            .thenReturn(userIds)

        val result = userRepository.getUsers(true)

        assert(result is Result.Success)

        when(result){
            is Result.Success ->
                assert(result.value == users.toList())
        }
    }

    @Test
    fun `refresh users Test database operation failure`() = runBlocking {

        `when`(userDao.count())
            .thenReturn(0)

        val response = mockResponse<List<User>>()

        `when`(response.code())
            .thenReturn(200)

        `when`(response.body())
            .thenReturn(users)

        `when`(userService.getUsers())
            .thenReturn(response)

        `when`(userDao.insertAll(users))
            .thenReturn(incompleteUserIds)

        val result = userRepository.getUsers()

        assert(result is Result.Failure)

        when(result){
            is Result.Failure ->
                assert(result.reason == FailureReason.DATABASE_OPERATION_ERROR)
        }
    }

    @Test
    fun `refresh users Test null body failure`() = runBlocking {

        `when`(userDao.count())
            .thenReturn(0)

        val response = mockResponse<List<User>>()

        `when`(response.code())
            .thenReturn(200)

        `when`(response.body())
            .thenReturn(null)

        `when`(userService.getUsers())
            .thenReturn(response)

        val result = userRepository.getUsers(true)

        assert(result is Result.Failure)

        when(result){
            is Result.Failure ->
                assert(result.reason == FailureReason.INTERNAL_ERROR)
        }
    }

    @Test
    fun `get users Test success`() = runBlocking {

        `when`(userDao.count())
            .thenReturn(users.size)

        `when`(userDao.getUsers())
            .thenReturn(users)

        val result = userRepository.getUsers()

        assert(result is Result.Success)

        when(result){
            is Result.Success ->
                assert(result.value == users.toList())
        }
    }

    @Test
    fun `get users failure internal server error test`() = runBlocking {

        val response = mockResponse<List<User>>()

        `when`(response.code())
            .thenReturn(500)

        `when`(response.body())
            .thenReturn(null)

        `when`(userService.getUsers())
            .thenReturn(response)

        val result = userRepository.getUsers(true)

        assert(result is Result.Failure)

        when(result){
            is Result.Failure ->
                assert(result.reason == FailureReason.INTERNAL_ERROR)
        }
    }

    @Test
    fun `get users failure server not found test`() = runBlocking {

        `when`(userDao.count())
            .thenReturn(0)

        `when`(userService.getUsers())
            .thenAnswer { throw UnknownHostException() }

        val result = userRepository.getUsers(true)

        assert(result is Result.Failure)

        when(result){
            is Result.Failure -> {
                assert(result.reason == FailureReason.SERVER_COULDNT_BE_FOUND)
            }
        }
    }

    @Test
    fun `get users failure throw any exception test`() = runBlocking {

        `when`(userDao.count())
            .thenReturn(0)

        `when`(userService.getUsers())
            .thenAnswer { throw SocketTimeoutException() }

        val result = userRepository.getUsers(true)

        assert(result is Result.Failure)

        when(result){
            is Result.Failure ->
                assert(result.reason == FailureReason.INTERNAL_ERROR)
        }
    }
}