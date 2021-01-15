package com.luisfelipe.movie.data.remote.model

data class MovieResponse(
    val id: Int,
    val title: String,
    val vote_count: Int,
    val popularity: Int,
    val backdrop_path: String
)
