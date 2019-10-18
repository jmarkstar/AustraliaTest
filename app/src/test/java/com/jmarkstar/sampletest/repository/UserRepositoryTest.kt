package com.jmarkstar.sampletest.repository

import com.jmarkstar.sampletest.incompleteUserIds
import com.jmarkstar.sampletest.repository.local.daos.UserDao
import com.jmarkstar.sampletest.repository.network.UserService
import com.jmarkstar.sampletest.users
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response
import com.jmarkstar.sampletest.models.User
import com.jmarkstar.sampletest.userIds
import okhttp3.ResponseBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UserRepositoryTest {

    private val userService: UserService = mock(UserService::class.java)

    private val userDao: UserDao = mock(UserDao::class.java)

    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        userRepository = UserRepositoryImpl(userService, userDao)
    }

    @Test
    fun `refresh users Test success`() = runBlocking {

        `when`(userDao.count())
            .thenReturn(0)

        `when`(userService.getUsers())
            .thenReturn(Response.success(200, users.toList()))

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

        `when`(userService.getUsers())
            .thenReturn(Response.success(200, users))

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

        `when`(userService.getUsers())
            .thenReturn(Response.success<List<User>>(200, null))

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

        val getUsersResponse = Response.error<List<User>>(500, ResponseBody.create(null, ""))

        `when`(userService.getUsers())
            .thenReturn(getUsersResponse)

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