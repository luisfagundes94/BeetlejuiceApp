package com.luisfelipe.movie.data.remote.repository_impl

import com.luisfelipe.movie.data.remote.service.TheMovieDbService
import com.luisfelipe.movie.domain.model.Movie
import com.luisfelipe.movie.domain.repository.Repository
import kotlinx.coroutines.withTimeout

class RepositoryImpl(private val theMovieDbService: TheMovieDbService): Repository {

    private companion object {
        private const val MIN_RESPONSE_CODE = 200
        private const val MAX_RESPONSE_CODE = 299
        const val REQUEST_TIMEOUT = 5000L
    }

    override suspend fun getMovieDetails(movieId: String): Movie {

    }

    override suspend fun getSimilarMovies(movieId: String): List<Movie> {
        return emptyList()
    }
}