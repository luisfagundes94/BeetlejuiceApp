package com.luisfelipe.movie.domain.usecase

import com.luisfelipe.movie.domain.repository.FavoritesRepository

class GetIsFavoriteMovieFromCache(private val repository: FavoritesRepository) {
    suspend operator fun invoke(movieId: String) = repository.getIsFavoriteMovie(movieId)
}
