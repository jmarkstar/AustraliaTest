package com.jmarkstar.sampletest.repository

import org.koin.test.KoinTest
import org.mockito.Mockito
import retrofit2.Response

open class BaseRepositoryTest: KoinTest {

    @Suppress("UNCHECKED_CAST")
    protected fun <T> mockResponse(): Response<T> {
        return Mockito.mock(Response::class.java) as Response<T>
    }
}