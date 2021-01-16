package com.luisfelipe.movie.data.remote.service

import com.luisfelipe.movie.BuildConfig
import com.luisfelipe.movie.data.remote.model.GenresBodyResponse
import com.luisfelipe.movie.data.remote.model.MovieResponse
import com.luisfelipe.movie.data.remote.model.SimilarMoviesBodyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbService {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String = BuildConfig.THE_MOVIE_DB_API_KEY
    ): Response<MovieResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DB_API_KEY,
        @Query("page") pageNumber: Int,
    ): Response<SimilarMoviesBodyResponse>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DB_API_KEY
    ): Response<GenresBodyResponse>
}