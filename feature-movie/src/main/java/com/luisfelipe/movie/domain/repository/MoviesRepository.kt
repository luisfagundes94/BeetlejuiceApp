package com.luisfelipe.movie.domain.repository

import com.luisfelipe.movie.domain.enums.ResultStatus
import com.luisfelipe.movie.domain.model.Genre
import com.luisfelipe.movie.domain.model.Movie
import com.luisfelipe.movie.domain.model.SimilarMovie

interface MoviesRepository {
    suspend fun getMovieDetails(movieId: Int): ResultStatus<Movie>
    suspend fun getSimilarMovies(movieId: Int, pageNumber: Int): ResultStatus<List<SimilarMovie>>
    suspend fun getMovieGenres(): ResultStatus<List<Genre>>
}