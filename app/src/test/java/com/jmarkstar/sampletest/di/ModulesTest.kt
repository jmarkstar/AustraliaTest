package com.jmarkstar.sampletest.di

import androidx.room.Room
import com.jmarkstar.sampletest.repository.local.SampleTestDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseTestModule: Module = module {

    single {
        Room.inMemoryDatabaseBuilder(androidContext(), SampleTestDatabase::class.java).build()
    }
}