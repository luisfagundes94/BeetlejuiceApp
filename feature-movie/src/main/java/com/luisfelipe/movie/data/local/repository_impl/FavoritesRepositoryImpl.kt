package com.luisfelipe.movie.data.local.repository_impl

import com.luisfelipe.movie.data.local.cache.FavoritesCache
import com.luisfelipe.movie.domain.repository.FavoritesRepository

class FavoritesRepositoryImpl(private val favoritesCache: FavoritesCache): FavoritesRepository {

    override suspend fun getIsFavoriteMovie(movieId: String) =
        favoritesCache.getIsFavoriteMovie(movieId)

    override suspend fun setIsFavoriteMovie(movieId: String, isFavorite: Boolean) =
        favoritesCache.setIsFavoriteMovie(movieId, isFavorite)
    
}