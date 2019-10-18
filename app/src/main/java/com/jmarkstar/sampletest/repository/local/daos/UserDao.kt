package com.jmarkstar.sampletest.repository.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jmarkstar.sampletest.models.User

@Dao interface UserDao {

    @Query("SELECT COUNT(id) FROM users")
    suspend fun count(): Int

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newUsers: List<User>): List<Long>

    @Query("DELETE FROM users")
    suspend fun deleteAll(): Int
}