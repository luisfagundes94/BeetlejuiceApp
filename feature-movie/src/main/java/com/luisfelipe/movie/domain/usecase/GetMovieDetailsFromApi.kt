package com.luisfelipe.movie.domain.usecase

import com.luisfelipe.movie.domain.repository.Repository

class GetMovieDetailsFromApi(private val repository: Repository) {
    suspend operator fun invoke(movieId: Int) = repository.getMovieDetails(movieId)
}