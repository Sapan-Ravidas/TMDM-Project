package com.sapan.tmdbapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "genre_table")
data class GenreData(
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)