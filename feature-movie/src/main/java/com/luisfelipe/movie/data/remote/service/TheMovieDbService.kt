package com.luisfelipe.movie.data.remote.service

import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDbService {

    @GET("/movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int)
}