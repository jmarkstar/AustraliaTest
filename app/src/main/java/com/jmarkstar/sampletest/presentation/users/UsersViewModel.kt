package com.jmarkstar.sampletest.presentation.users

import androidx.lifecycle.*
import com.jmarkstar.sampletest.models.User
import com.jmarkstar.sampletest.repository.FailureReason
import com.jmarkstar.sampletest.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.jmarkstar.sampletest.repository.Result

class UsersViewModel constructor(private val userRepository: UserRepository): ViewModel() {

    private var _users = MutableLiveData<List<User>>()
    private var _isLoading = MutableLiveData<Boolean>()
    private var _error = MutableLiveData<FailureReason>()

    val users: LiveData<List<User>> = _users
    val isLoading: LiveData<Boolean> = _isLoading
    val isUsersEmpty: LiveData<Boolean> = Transformations.map(_users) { it.isEmpty() }
    val error: LiveData<FailureReason> = _error

    var selectedUser = MutableLiveData<User>()

    init {
        getUsers()
    }

    private fun getUsers(refresh: Boolean = false) = viewModelScope.launch {

        _isLoading.value = true

        withContext(Dispatchers.Default) {

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

    private suspend fun setUsers(items: List<User>) = withContext(Dispatchers.Main){
        _users.value = items
    }

    private suspend fun setError(reason: FailureReason) = withContext(Dispatchers.Main){
        _error.value = reason
    }

    fun select(user: User){
        selectedUser.value = user
    }
}