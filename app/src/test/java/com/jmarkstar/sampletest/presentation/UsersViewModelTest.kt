package com.jmarkstar.sampletest.presentation

import com.jmarkstar.sampletest.models.User
import com.jmarkstar.sampletest.presentation.users.UsersViewModel
import com.jmarkstar.sampletest.repository.FailureReason
import com.jmarkstar.sampletest.repository.UserRepository
import com.jmarkstar.sampletest.users
import org.mockito.Mockito.*
import com.jmarkstar.sampletest.repository.Result
import com.jmarkstar.sampletest.user1
import com.jraska.livedata.test
import kotlinx.coroutines.*
import org.junit.*

@ExperimentalCoroutinesApi
class UsersViewModelTest: BaseViewModelTest() {

    private val usersRepository: UserRepository = mock(UserRepository::class.java)

    private lateinit var usersViewModel: UsersViewModel

    @Before fun before() {
        usersViewModel = UsersViewModel(usersRepository, testCoroutineConextProvider)
    }

    @Test fun `get users test success`() {

        runBlocking {
            `when`(usersRepository.getUsers())
                .thenReturn(Result.Success(users))
        }

        usersViewModel.getUsers()

        usersViewModel
            .users
            .test()
            .assertHasValue()
            .assertValue(users)

        usersViewModel
            .isUsersEmpty
            .test()
            .assertHasValue()
            .assertValue(false)

        usersViewModel
            .error
            .test()
            .assertNoValue()
    }

    @Test fun `get empty list test success`() {

        val emptyList = emptyList<User>()

        runBlocking {
            `when`(usersRepository.getUsers())
                .thenReturn(Result.Success(emptyList))
        }

        usersViewModel.getUsers()

        usersViewModel
            .users
            .test()
            .assertHasValue()
            .assertValue(emptyList)

        usersViewModel
            .isUsersEmpty
            .test()
            .assertHasValue()
            .assertValue(true)

        usersViewModel
            .error
            .test()
            .assertNoValue()
    }

    @Test fun `get error test`() {

        runBlocking {
            `when`(usersRepository.getUsers())
                .thenReturn(Result.Failure(FailureReason.INTERNAL_ERROR))
        }

        usersViewModel.getUsers()

        usersViewModel
            .users
            .test()
            .assertNoValue()

        usersViewModel
            .isUsersEmpty
            .test()
            .assertNoValue()

        usersViewModel
            .error
            .test()
            .assertHasValue()
            .assertValue(FailureReason.INTERNAL_ERROR)
    }

    @Test fun `select user test`() {

        usersViewModel.select(user1)

        usersViewModel
            .selectedUser
            .test()
            .assertHasValue()
            .assertValue(user1)
    }
}