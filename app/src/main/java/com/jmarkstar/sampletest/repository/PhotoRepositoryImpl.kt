package com.jmarkstar.sampletest.repository

import com.jmarkstar.sampletest.models.FailureReason
import com.jmarkstar.sampletest.models.Photo
import com.jmarkstar.sampletest.models.Result
import com.jmarkstar.sampletest.models.User
import com.jmarkstar.sampletest.repository.local.daos.PhotoDao
import com.jmarkstar.sampletest.repository.network.PhotoService
import com.jmarkstar.sampletest.repository.network.processError
import com.jmarkstar.sampletest.repository.network.processNetworkResult

class PhotoRepositoryImpl(private val photoService: PhotoService, private val photoDao: PhotoDao): PhotoRepository {

    override suspend fun getUserPhotos(user: User, refresh: Boolean): Result<List<Photo>> {

        try {
            if(!refresh && photoDao.count() > 0){
                return Result.Success(photoDao.getPhotosByUserId(user.id))
            }

            val getPhotosResult = photoService.getPhotos()

            return processNetworkResult(getPhotosResult.code()) network@ {

                val photos = checkNotNull(getPhotosResult.body()){
                    return@network Result.Failure(FailureReason.INTERNAL_ERROR)
                }

                photoDao.deleteAll()
                if(photoDao.insertAll(photos).size != photos.size){
                    return@network Result.Failure(FailureReason.DATABASE_OPERATION_ERROR)
                }

                Result.Success(photos.filter{it.albumId == user.id})
            }
        } catch(ex: Exception) {
            return processError(ex)
        }
    }
}