package com.jmarkstar.sampletest.repository.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jmarkstar.sampletest.models.Photo

@Dao interface PhotoDao {

    @Query("SELECT COUNT(id) FROM photos")
    suspend fun count(): Int

    @Query("SELECT * FROM photos WHERE albumId = :userId")
    suspend fun getPhotosByUserId(userId: Int): List<Photo>

    @Query("SELECT * FROM photos WHERE id = :id")
    suspend fun getPhotoById(id: Int): Photo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newPhotos: List<Photo>): List<Long>

    @Query("DELETE FROM photos")
    suspend fun deleteAll(): Int


}