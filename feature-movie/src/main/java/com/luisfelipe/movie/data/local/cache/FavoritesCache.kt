package com.luisfelipe.movie.data.local.cache

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.firstOrNull

class FavoritesCache(context: Context) {

    private companion object {
        const val DATA_STORE_NAME = "FAVORITES"
        const val DEFAULT_VALUE = false
    }

    private val dataStore = context.createDataStore(name = DATA_STORE_NAME)

    suspend fun getIsFavoriteMovie(movieId: String): Boolean {
        val preferences = dataStore.data.firstOrNull()
        val preferencesKey = booleanPreferencesKey(movieId)
        return preferences?.get(preferencesKey) ?: DEFAULT_VALUE
    }

    suspend fun setIsFavoriteMovie(movieId: String, isFavorite: Boolean) {
        dataStore.edit { preferences ->
            val preferencesKey = booleanPreferencesKey(movieId)
            preferences[preferencesKey] = isFavorite
        }
    }

}