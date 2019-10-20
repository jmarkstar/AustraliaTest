package com.jmarkstar.sampletest.presentation.users

import androidx.lifecycle.*
import com.jmarkstar.sampletest.models.User
import com.jmarkstar.sampletest.presentation.common.BaseViewModel
import com.jmarkstar.sampletest.presentation.common.CoroutineContextProvider
import com.jmarkstar.sampletest.repository.FailureReason
import com.jmarkstar.sampletest.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.jmarkstar.sampletest.repository.Result

class UsersViewModel constructor(private val userRepository: UserRepository,
                                 coroutineContextProvider: CoroutineContextProvider): BaseViewModel(coroutineContextProvider) {

    private var _users = MutableLiveData<List<User>>()
    private var _isLoading = MutableLiveData<Boolean>()
    private var _error = MutableLiveData<FailureReason>()
    private var _selectedUser = MutableLiveData<User>()

    val users: LiveData<List<User>> = _users
    val isLoading: LiveData<Boolean> = _isLoading
    val isUsersEmpty: LiveData<Boolean> = Transformations.map(_users) { it.isEmpty() }
    val error: LiveData<FailureReason> = _error
    val selectedUser: LiveData<User> = _selectedUser

    init {
        getUsers()
    }

    fun getUsers(refresh: Boolean = false) = launch {

        _isLoading.value = true

        withContext(coroutineContextProvider.Default) {

            when( val result = userRepository.getUsers(refresh)) {

                is Result.Success -> {
                    setUsers(result.value)
                }
                is Result.Failure -> {
                    setError(result.reason)
                }
            }
        }

        _isLoading.value = false
    }

    private suspend fun setUsers(items: List<User>) = withContext(coroutineContextProvider.Main){
        _users.value = items
    }

    private suspend fun setError(reason: FailureReason) = withContext(coroutineContextProvider.Main){
        _error.value = reason
    }

    fun select(user: User){
        _selectedUser.value = user
    }
}