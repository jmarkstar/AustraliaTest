package com.jmarkstar.sampletest.repository

import com.jmarkstar.sampletest.models.Result
import com.jmarkstar.sampletest.models.User

interface UserRepository {

    suspend fun getUsers(refresh: Boolean = false): Result<List<User>>
}