package com.luisfelipe.movie.domain.usecase

import com.luisfelipe.movie.domain.repository.MoviesRepository

class GetMovieGenresFromApi(private val repository: MoviesRepository) {
    suspend operator fun invoke() = repository.getMovieGenres()
}