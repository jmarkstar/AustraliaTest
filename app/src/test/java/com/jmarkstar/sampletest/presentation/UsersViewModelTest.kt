package com.jmarkstar.sampletest.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jmarkstar.sampletest.models.User
import com.jmarkstar.sampletest.presentation.common.CoroutineContextProvider
import com.jmarkstar.sampletest.presentation.common.TestCoroutineContextProvider
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
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@ExperimentalCoroutinesApi
class UsersViewModelTest: KoinTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    private val usersRepository: UserRepository = mock(UserRepository::class.java)

    private val usersViewModel: UsersViewModel by inject()

    @Before fun before() {

        startKoin {
            modules(listOf(module {
                single { usersRepository }
                single<CoroutineContextProvider> { TestCoroutineContextProvider() }
                viewModel { UsersViewModel( userRepository = get(), coroutineContextProvider = get()) }
            }))
        }
    }

    @After fun after(){
        stopKoin()
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