package com.jmarkstar.sampletest.repository.network

import android.os.Build
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1], manifest = Config.NONE)
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