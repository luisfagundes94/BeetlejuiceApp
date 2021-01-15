package com.luisfelipe.movie.domain.repository

import com.luisfelipe.movie.domain.model.Movie

interface Repository {
    suspend fun getMovieDetails(movieId: String): Movie
    suspend fun getSimilarMovies(movieId: String): List<Movie>
}