package com.jmarkstar.sampletest.presentation.users

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmarkstar.sampletest.extensions.setLoading
import com.jmarkstar.sampletest.models.User
import com.jmarkstar.sampletest.presentation.Resource
import com.jmarkstar.sampletest.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.jmarkstar.sampletest.repository.Result

class UsersViewModel constructor(private val userRepository: UserRepository): ViewModel() {

    var users = MutableLiveData<Resource<List<User>>>()

    var selectedUser = MutableLiveData<User>()

    init {
        users.value = Resource.Empty
        getUsers()
    }

    fun getUsers(refresh: Boolean = true) = viewModelScope.launch {
        users.setLoading()

        val usersResult = withContext(Dispatchers.Default) {

            when( val result = userRepository.getUsers(refresh)) {

                is Result.Success -> {
                    Log.v("UsersViewModel","getUsers size ${result.value.size}")
                    if(result.value.isNotEmpty()) {
                        Resource.Success(result.value)
                    } else {
                        Resource.Empty
                    }
                }
                is Result.Failure -> {
                    Resource.Error(result.reason)
                }
            }
        }

        users.value = usersResult
    }

    fun select(user: User){
        selectedUser.value = user
    }
}