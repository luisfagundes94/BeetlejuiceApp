package com.luisfelipe.movie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luisfelipe.movie.data.local.model.GenreData

@Dao
interface GenreDao {

    @Query("SELECT * FROM genres")
    suspend fun getGenres(): List<GenreData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreData>)
}
