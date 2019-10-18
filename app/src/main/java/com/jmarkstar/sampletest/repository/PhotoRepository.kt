package com.jmarkstar.sampletest.repository

import com.jmarkstar.sampletest.models.Photo
import com.jmarkstar.sampletest.models.Result
import com.jmarkstar.sampletest.models.User

interface PhotoRepository {

    suspend fun getUserPhotos(user: User, refresh: Boolean = false): Result<List<Photo>>
}