package com.luisfelipe.movie.domain.usecase

import com.luisfelipe.movie.domain.repository.Repository

class GetSimilarMoviesFromApi(private val repository: Repository) {
    suspend operator fun invoke(movieId: Int) = repository.getSimilarMovies(movieId)
}