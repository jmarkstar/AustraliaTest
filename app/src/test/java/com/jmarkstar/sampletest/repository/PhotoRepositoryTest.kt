package com.jmarkstar.sampletest.repository

import com.jmarkstar.sampletest.incompletePhotoIds
import com.jmarkstar.sampletest.photoIds
import com.jmarkstar.sampletest.photos
import com.jmarkstar.sampletest.repository.local.daos.PhotoDao
import com.jmarkstar.sampletest.repository.network.PhotoService
import com.jmarkstar.sampletest.user1
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response
import com.jmarkstar.sampletest.models.FailureReason
import com.jmarkstar.sampletest.models.Photo
import com.jmarkstar.sampletest.models.Result
import okhttp3.ResponseBody
import java.net.UnknownHostException

class PhotoRepositoryTest {

    private val photoService: PhotoService = mock(PhotoService::class.java)

    private val photoDao: PhotoDao = mock(PhotoDao::class.java)

    private lateinit var photoRepository: PhotoRepository

    @Before
    fun setup() {
        photoRepository = PhotoRepositoryImpl(photoService, photoDao)
    }

    @Test
    fun `refresh photos Test success`() = runBlocking {

        `when`(photoDao.count())
            .thenReturn(0)

        `when`(photoService.getPhotos())
            .thenReturn(Response.success(200, photos))

        `when`(photoDao.insertAll(photos))
            .thenReturn(photoIds)

        val result = photoRepository.getUserPhotos(user1)

        assert(result is Result.Success)

        when(result){
            is Result.Success ->
                assert(result.value == photos.filter { it.albumId == user1.id })
        }
    }

    @Test
    fun `refresh photos Test database operation failure`() = runBlocking {

        `when`(photoDao.count())
            .thenReturn(0)

        `when`(photoService.getPhotos())
            .thenReturn(Response.success(200, photos))

        `when`(photoDao.insertAll(photos))
            .thenReturn(incompletePhotoIds)

        val result = photoRepository.getUserPhotos(user1)

        assert(result is Result.Failure)

        when(result){
            is Result.Failure ->
                assert(result.reason == FailureReason.DATABASE_OPERATION_ERROR)
        }
    }

    @Test
    fun `refresh users Test null body failure`() = runBlocking {

        `when`(photoDao.count())
            .thenReturn(0)

        `when`(photoService.getPhotos())
            .thenReturn(Response.success<List<Photo>>(200, null))

        val result = photoRepository.getUserPhotos(user1)

        assert(result is Result.Failure)

        when(result){
            is Result.Failure ->
                assert(result.reason == FailureReason.INTERNAL_ERROR)
        }
    }

    @Test
    fun `get photos Test success`() = runBlocking {

        val photosFiltered = photos.filter { it.albumId == user1.id }

        `when`(photoDao.count())
            .thenReturn(photosFiltered.size)

        `when`(photoDao.getPhotosByUserId(user1.id))
            .thenReturn(photosFiltered)

        val result = photoRepository.getUserPhotos(user1)

        assert(result is Result.Success)

        when(result){
            is Result.Success ->
                assert(result.value == photosFiltered.toList())
        }
    }

    @Test
    fun `get photos failure internal server error test`() = runBlocking {

        val getUsersResponse = Response.error<List<Photo>>(500, ResponseBody.create(null, ""))

        `when`(photoDao.count())
            .thenReturn(0)

        `when`(photoService.getPhotos())
            .thenReturn(getUsersResponse)

        val result = photoRepository.getUserPhotos(user1)

        assert(result is Result.Failure)

        when(result){
            is Result.Failure ->
                assert(result.reason == FailureReason.INTERNAL_ERROR)
        }
    }

    @Test
    fun `get photos failure server not found test`() = runBlocking {

        `when`(photoDao.count())
            .thenReturn(0)

        `when`(photoService.getPhotos())
            .thenAnswer { throw UnknownHostException() }

        val result = photoRepository.getUserPhotos(user1)

        assert(result is Result.Failure)

        when(result){
            is Result.Failure -> {
                assert(result.reason == FailureReason.SERVER_COULDNT_BE_FOUND)
            }
        }
    }
}