package com.jmarkstar.sampletest.models

import androidx.room.Entity
import androidx.room.Index
import com.squareup.moshi.JsonClass

@Entity(tableName = "photos",
    indices = [Index("id")],
    primaryKeys = ["id"])

@JsonClass(generateAdapter = true)
class Photo(val id: Int,
            val albumId: Int,
            val title: String,
            val url: String,
            val thumbnailUrl: String)