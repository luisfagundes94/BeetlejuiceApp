package com.luisfelipe.movie.data.remote.service

import com.luisfelipe.movie.data.remote.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDbService {

    @GET("/movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): Response<MovieResponse>
}