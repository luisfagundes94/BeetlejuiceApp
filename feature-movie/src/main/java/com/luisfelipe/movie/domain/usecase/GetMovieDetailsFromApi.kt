package com.luisfelipe.movie.domain.usecase

import com.luisfelipe.movie.domain.repository.MoviesRepository

class GetMovieDetailsFromApi(private val repository: MoviesRepository) {
    suspend operator fun invoke(movieId: Int) = repository.getMovieDetails(movieId)
}
