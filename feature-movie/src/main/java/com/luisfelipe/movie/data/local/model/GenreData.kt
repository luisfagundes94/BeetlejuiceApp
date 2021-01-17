package com.luisfelipe.movie.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreData(
    @PrimaryKey
    val id: Int,
    val name: String
)