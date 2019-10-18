package com.jmarkstar.sampletest.repository.network

import androidx.test.core.app.ApplicationProvider
import com.jmarkstar.sampletest.di.commonModule
import com.jmarkstar.sampletest.di.constantModule
import com.jmarkstar.sampletest.di.networkModule
import org.junit.After
import org.junit.Before
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

abstract class BaseNetworkTest: KoinTest {

    @Before
    fun setupKoinModules(){
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(listOf(commonModule, networkModule, constantModule))
        }
    }

    @After
    fun stopKoinModules(){
        stopKoin()
    }
}