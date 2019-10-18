package com.jmarkstar.sampletest.repository.local

import android.os.Build
import com.jmarkstar.sampletest.photo1
import com.jmarkstar.sampletest.photos
import com.jmarkstar.sampletest.repository.local.daos.PhotoDao
import com.jmarkstar.sampletest.user1
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class PhotoDaoTest: BaseLocalTest() {

    private val photoDao: PhotoDao by inject()

    @Test
    fun `insert photos test success`()= runBlocking {
        val result = photoDao.insertAll(photos)
        Assert.assertEquals(photos.size, result.size)
    }

    @Test
    fun `get count test success`()= runBlocking {
        photoDao.insertAll(photos)

        val count = photoDao.count()
        Assert.assertEquals(photos.size, count)
    }

    @Test
    fun `get photos by user test success`() = runBlocking {
        photoDao.insertAll(photos)

        val photos = photoDao.getPhotosByUserId(user1.id)
        Assert.assertEquals(photos.size, 2)
    }

    @Test
    fun `get photo by id test success`() = runBlocking {
        photoDao.insertAll(photos)

        val photoResult = photoDao.getPhotoById(1)
        Assert.assertNotNull(photoResult)
        Assert.assertEquals(photoResult!!.albumId, photo1.albumId)
    }

    @Test
    fun `delete photos test success`() = runBlocking {
        photoDao.insertAll(photos)

        val affectedRows = photoDao.deleteAll()
        Assert.assertEquals(affectedRows, photos.size)
    }

}