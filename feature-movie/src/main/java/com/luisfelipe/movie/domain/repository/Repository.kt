package com.luisfelipe.movie.domain.repository

import com.luisfelipe.movie.domain.enums.ResultStatus
import com.luisfelipe.movie.domain.model.Movie

interface Repository {
    suspend fun getMovieDetails(movieId: Int): ResultStatus<Movie>
    suspend fun getSimilarMovies(movieId: Int): ResultStatus<List<Movie>>
}