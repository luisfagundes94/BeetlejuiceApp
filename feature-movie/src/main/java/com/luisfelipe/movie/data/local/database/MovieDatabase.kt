package com.luisfelipe.movie.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luisfelipe.movie.data.local.dao.GenreDao
import com.luisfelipe.movie.data.local.model.GenreData

@Database(version = 1, entities = [GenreData::class])
abstract class MovieDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "MOVIES"
    }

    abstract fun genreDao(): GenreDao
}
