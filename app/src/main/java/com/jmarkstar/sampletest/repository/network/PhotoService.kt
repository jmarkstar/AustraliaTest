package com.jmarkstar.sampletest.repository.network

import com.jmarkstar.sampletest.models.Photo
import retrofit2.Response
import retrofit2.http.GET

interface PhotoService {

    @GET("photos")
    suspend fun getPhotos(): Response<List<Photo>>
}