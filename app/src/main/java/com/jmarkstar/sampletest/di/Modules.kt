package com.jmarkstar.sampletest.di

import androidx.room.Room
import com.jmarkstar.sampletest.BuildConfig
import com.jmarkstar.sampletest.presentation.users.UsersViewModel
import com.jmarkstar.sampletest.repository.PhotoRepository
import com.jmarkstar.sampletest.repository.PhotoRepositoryImpl
import com.jmarkstar.sampletest.repository.UserRepository
import com.jmarkstar.sampletest.repository.UserRepositoryImpl
import com.jmarkstar.sampletest.repository.local.SampleTestDatabase
import com.jmarkstar.sampletest.repository.network.PhotoService
import com.jmarkstar.sampletest.repository.network.UserService
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val constantModule: Module = module {

    single(named("debug")) { BuildConfig.DEBUG }

    single(named("baseUrl")) { BuildConfig.BASE_URL }
}

val viewModelModule: Module = module {

    viewModel { UsersViewModel( userRepository = get()) }
}

/** Repository Module */

val repositoryModule: Module = module {

    single<UserRepository> { UserRepositoryImpl(userService = get(), userDao = get()) }
    single<PhotoRepository> { PhotoRepositoryImpl(photoService = get(), photoDao = get()) }
}

/** Database Module */

val daoModule: Module = module {

    single { get<SampleTestDatabase>().userDao }
    single { get<SampleTestDatabase>().photoDao }
}

val databaseModule: Module = module {

    single {
        Room.databaseBuilder(androidContext(), SampleTestDatabase::class.java, "sampletest-db")
            .build()
    }
}

/** Network Module */

val networkModule: Module = module {

    //Services

    single { get<Retrofit>().create(UserService::class.java) }
    single { get<Retrofit>().create(PhotoService::class.java) }

    //HTTP Client

    single {

        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()
        if (get(named("debug"))) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        clientBuilder.build()
    }

    single {

        Retrofit.Builder()
            .baseUrl(get<String>(named("baseUrl")))
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
}

val commonModule: Module = module {

    single {
        Moshi.Builder()
            .build()
    }
}