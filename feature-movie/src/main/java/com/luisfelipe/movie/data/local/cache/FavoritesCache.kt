package com.luisfelipe.movie.data.local.cache

import android.content.SharedPreferences

class FavoritesCache(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val SHARED_PREFERENCES_NAME = "FAVORITES"
        const val DEFAULT_VALUE = false
    }

    fun getIsFavoriteMovie(movieId: String) = sharedPreferences.getBoolean(movieId, DEFAULT_VALUE)

    fun setIsFavoriteMovie(movieId: String, isFavorite: Boolean) =
        sharedPreferences.edit().putBoolean(movieId, isFavorite).apply()
}
