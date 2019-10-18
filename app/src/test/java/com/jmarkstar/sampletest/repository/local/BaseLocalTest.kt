package com.jmarkstar.sampletest.repository.local

import androidx.test.core.app.ApplicationProvider
import com.jmarkstar.sampletest.di.commonModule
import com.jmarkstar.sampletest.di.daoModule
import com.jmarkstar.sampletest.di.databaseTestModule
import org.junit.After
import org.junit.Before
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

abstract class BaseLocalTest: KoinTest {

    @Before
    fun setup() {

        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(listOf(commonModule, databaseTestModule, daoModule))
        }
    }

    @After
    fun unsetup() {
        stopKoin()
    }
}