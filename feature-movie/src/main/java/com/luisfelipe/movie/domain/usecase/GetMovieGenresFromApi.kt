package com.luisfelipe.movie.domain.usecase

import com.luisfelipe.movie.domain.repository.Repository

class GetMovieGenresFromApi(private val repository: Repository) {
    suspend operator fun invoke() = repository.getMovieGenres()
}