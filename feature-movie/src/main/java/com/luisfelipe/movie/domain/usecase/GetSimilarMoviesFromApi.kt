package com.luisfelipe.movie.domain.usecase

import com.luisfelipe.movie.domain.repository.MoviesRepository

class GetSimilarMoviesFromApi(private val repository: MoviesRepository) {
    suspend operator fun invoke(movieId: Int) = repository.getSimilarMovies(movieId)
}