package com.luisfelipe.movie.data.remote.repository_impl

import com.luisfelipe.movie.data.remote.mapper.MovieMapper
import com.luisfelipe.movie.data.remote.service.TheMovieDbService
import com.luisfelipe.movie.domain.enums.ResultStatus
import com.luisfelipe.movie.domain.model.Movie
import com.luisfelipe.movie.domain.repository.Repository
import kotlinx.coroutines.withTimeout
import java.io.IOException

class RepositoryImpl(private val theMovieDbService: TheMovieDbService): Repository {

    private companion object {
        private const val MIN_RESPONSE_CODE = 200
        private const val MAX_RESPONSE_CODE = 299
        const val REQUEST_TIMEOUT = 5000L
    }

    override suspend fun getMovieDetails(movieId: Int): ResultStatus<Movie> {
        return withTimeout(REQUEST_TIMEOUT) {
            try {
                val response = theMovieDbService.getMovieDetails(movieId)
                if (response.code() in MIN_RESPONSE_CODE..MAX_RESPONSE_CODE) {
                    val movie = response.body()?.let { MovieMapper.mapResponseToDomain(it) }
                    return@withTimeout ResultStatus.Success(movie as Movie)
                } else return@withTimeout ResultStatus.Error(response.message())
            } catch (exception: IOException) {
                return@withTimeout ResultStatus.Error(exception.message.toString())
            }
        }
    }

    override suspend fun getSimilarMovies(movieId: Int): ResultStatus<List<Movie>> {
        TODO("Not yet implemented")
    }

}