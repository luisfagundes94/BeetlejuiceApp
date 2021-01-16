package com.luisfelipe.movie.data.remote.repository_impl

import com.luisfelipe.movie.data.mapper.GenreMapper
import com.luisfelipe.movie.data.mapper.MovieMapper
import com.luisfelipe.movie.data.remote.service.TheMovieDbService
import com.luisfelipe.movie.domain.enums.ResultStatus
import com.luisfelipe.movie.domain.model.Genre
import com.luisfelipe.movie.domain.model.Movie
import com.luisfelipe.movie.domain.model.SimilarMovie
import com.luisfelipe.movie.domain.repository.MoviesRepository
import kotlinx.coroutines.withTimeout
import java.io.IOException

class MoviesRepositoryImpl(private val theMovieDbService: TheMovieDbService): MoviesRepository {

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

    override suspend fun getSimilarMovies(movieId: Int, pageNumber: Int): ResultStatus<List<SimilarMovie>> {
        return withTimeout(REQUEST_TIMEOUT) {
            try {
                val response = theMovieDbService.getSimilarMovies(movieId, pageNumber = pageNumber)
                if (response.code() in MIN_RESPONSE_CODE..MAX_RESPONSE_CODE) {
                    val similarMovies = response.body()?.let { MovieMapper.mapResponseToDomain(it.results) }
                    return@withTimeout ResultStatus.Success(similarMovies as List<SimilarMovie>)
                } else return@withTimeout ResultStatus.Error(response.message())
            } catch (exception: IOException) {
                return@withTimeout ResultStatus.Error(exception.message.toString())
            }
        }
    }

    override suspend fun getMovieGenres(): ResultStatus<List<Genre>> {
        return withTimeout(REQUEST_TIMEOUT) {
            try {
                val response = theMovieDbService.getMovieGenres()
                if (response.code() in MIN_RESPONSE_CODE..MAX_RESPONSE_CODE) {
                    val genres = response.body()?.let { GenreMapper.mapResponseToDomain(it.genres) }
                    return@withTimeout ResultStatus.Success(genres as List<Genre>)
                } else return@withTimeout ResultStatus.Error(response.message())
            } catch (exception: IOException) {
                return@withTimeout ResultStatus.Error(exception.message.toString())
            }
        }
    }

}