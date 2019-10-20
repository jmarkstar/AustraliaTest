package com.jmarkstar.sampletest.repository.network

import com.jmarkstar.sampletest.di.*
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

abstract class BaseNetworkTest: KoinTest {

    @Before
    fun setupKoinModules(){
        startKoin {
            modules(listOf(commonModule, networkModule, serviceModule, constantTestModule))
        }
    }

    @After
    fun stopKoinModules(){
        stopKoin()
    }
}