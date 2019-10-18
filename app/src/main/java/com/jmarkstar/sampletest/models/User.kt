package com.jmarkstar.sampletest.models

import androidx.room.Entity
import androidx.room.Index
import com.squareup.moshi.JsonClass

@Entity(tableName = "users",
    indices = [Index("id")],
    primaryKeys = ["id"])

@JsonClass(generateAdapter = true)
data class User(val id: Int,
                val name: String,
                val email: String,
                val phone: String)