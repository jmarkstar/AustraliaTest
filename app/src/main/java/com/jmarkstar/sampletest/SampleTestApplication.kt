package com.jmarkstar.sampletest

import android.app.Application
import com.jmarkstar.sampletest.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SampleTestApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SampleTestApplication)
            androidLogger()

            modules(listOf(
                commonModule,
                networkModule,
                databaseModule,
                daoModule,
                repositoryModule,
                viewModelModule,
                constantModule))
        }
    }
}