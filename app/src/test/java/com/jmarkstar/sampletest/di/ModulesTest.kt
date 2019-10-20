package com.jmarkstar.sampletest.di

import androidx.room.Room
import com.jmarkstar.sampletest.repository.local.SampleTestDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val databaseTestModule: Module = module {

    single {
        Room.inMemoryDatabaseBuilder(androidContext(), SampleTestDatabase::class.java).build()
    }
}

val constantTestModule: Module = module {

    single(named("debug")) { true }

    single(named("baseUrl")) { "https://jsonplaceholder.typicode.com/" }
}