package com.luisfelipe.movie.domain.usecase

import com.luisfelipe.movie.domain.repository.FavoritesRepository

class SetIsFavoriteMovieToCache(private val repository: FavoritesRepository) {
    suspend operator fun invoke(movieId: String, isFavorite: Boolean) =
        repository.setIsFavoriteMovie(movieId, isFavorite)
}
