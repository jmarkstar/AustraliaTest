package com.jmarkstar.sampletest.repository.network

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.koin.test.inject

class PhotoServiceTest: BaseNetworkTest() {

    private val photoService: PhotoService by inject()

    @Test
    fun `get all photos success`() = runBlocking {

        val getAllResult = photoService.getPhotos()

        Assert.assertEquals(true, getAllResult.code() == 200)
        Assert.assertNotNull(getAllResult.body())
        Assert.assertEquals(true, getAllResult.body()!!.isNotEmpty())
    }
}