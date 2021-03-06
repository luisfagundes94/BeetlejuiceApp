package com.luisfelipe.movie.data

import com.luisfelipe.core.result.ResultStatus
import com.luisfelipe.movie.data.local.dao.GenreDao
import com.luisfelipe.movie.data.mapper.GenreMapper
import com.luisfelipe.movie.data.mapper.MovieMapper
import com.luisfelipe.movie.data.remote.service.TheMovieDbService
import com.luisfelipe.movie.domain.model.Genre
import com.luisfelipe.movie.domain.model.Movie
import com.luisfelipe.movie.domain.model.SimilarMovie
import com.luisfelipe.movie.domain.repository.MoviesRepository
import java.io.IOException
import kotlinx.coroutines.withTimeout

class MoviesRepositoryImpl(
    private val theMovieDbService: TheMovieDbService,
    private val genresDao: GenreDao
) : MoviesRepository {

    private companion object {
        private const val MIN_RESPONSE_CODE = 200
        private const val MAX_RESPONSE_CODE = 299
        const val REQUEST_TIMEOUT = 5000L
        private const val FIRST_PAGE = 1
        private const val PAGE_LIMIT = 10
    }

    private var pageNumber = FIRST_PAGE

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

    override suspend fun getSimilarMovies(
        movieId: Int
    ): ResultStatus<List<SimilarMovie>> {
        return withTimeout(REQUEST_TIMEOUT) {
            try {
                val response = theMovieDbService.getSimilarMovies(movieId, pageNumber = pageNumber)

                if (response.code() in MIN_RESPONSE_CODE..MAX_RESPONSE_CODE) {
                    val similarMovies = response.body()?.let {
                        MovieMapper.mapResponseToDomain(it.results, getGenres())
                    }
                    incrementPage()
                    return@withTimeout ResultStatus.Success(similarMovies as List<SimilarMovie>)
                } else return@withTimeout ResultStatus.Error(response.message())
            } catch (exception: IOException) {
                return@withTimeout ResultStatus.Error(exception.message.toString())
            }
        }
    }

    private suspend fun getGenres(): List<Genre> {
        val genreDataList = genresDao.getGenres()
        return if (genreDataList.isEmpty()) {
            val genreResponseList = theMovieDbService.getMovieGenres().body()?.genres
            val genreList =
                genreResponseList?.let { GenreMapper.mapResponseToDomain(it) } ?: emptyList()
            genresDao.insertGenres(GenreMapper.mapDomainToData(genreList))
            genreList
        } else GenreMapper.mapDataToDomain(genreDataList)
    }

    private fun incrementPage() {
        if (pageNumber < PAGE_LIMIT) pageNumber++
    }
}
