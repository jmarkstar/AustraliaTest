package com.jmarkstar.sampletest.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jmarkstar.sampletest.models.User
import com.jmarkstar.sampletest.presentation.common.TestCoroutineContextProvider
import com.jmarkstar.sampletest.presentation.users.UsersViewModel
import com.jmarkstar.sampletest.repository.UserRepository
import com.jmarkstar.sampletest.users
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import com.jmarkstar.sampletest.repository.Result
import kotlinx.coroutines.*
import org.junit.*

@ExperimentalCoroutinesApi
class UsersViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    private val testCoroutineConextProvider = TestCoroutineContextProvider()

    private val usersRepository: UserRepository = mock(UserRepository::class.java)

    private lateinit var usersViewModel: UsersViewModel

    private val observer: Observer<List<User>> = mock(Observer::class.java) as Observer<List<User>>

    @Before fun before() {
        usersViewModel = UsersViewModel(usersRepository, testCoroutineConextProvider)
        usersViewModel.users.observeForever(observer)
    }

    @Test fun `get users test success`() {

        runBlocking {
            `when`(usersRepository.getUsers())
                .thenReturn(Result.Success(users))
        }

        usersViewModel.getUsers()

        val captor: ArgumentCaptor<List<User>> = ArgumentCaptor.forClass(List::class.java) as ArgumentCaptor<List<User>>

        captor.run {
            verify(observer, times(1)).onChanged(capture())
            Assert.assertEquals(value, users)
        }
    }
}