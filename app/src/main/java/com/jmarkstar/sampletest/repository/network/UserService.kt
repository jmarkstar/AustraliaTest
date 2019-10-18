package com.jmarkstar.sampletest.repository.network

import com.jmarkstar.sampletest.models.User
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}