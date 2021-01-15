package com.luisfelipe.movie.domain.repository

interface FavoritesRepository {
    suspend fun getIsFavoriteMovie(movieId: String): Boolean
    suspend fun setIsFavoriteMovie(movieId: String, isFavorite: Boolean)
}