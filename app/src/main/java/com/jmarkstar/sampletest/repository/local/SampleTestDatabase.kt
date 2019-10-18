package com.jmarkstar.sampletest.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jmarkstar.sampletest.models.Photo
import com.jmarkstar.sampletest.models.User
import com.jmarkstar.sampletest.repository.local.daos.PhotoDao
import com.jmarkstar.sampletest.repository.local.daos.UserDao

@Database(entities = [
    User::class,
    Photo::class
],
    version = 1,
    exportSchema = false)
internal abstract class SampleTestDatabase: RoomDatabase() {

    abstract val userDao: UserDao
    abstract val photoDao: PhotoDao
}