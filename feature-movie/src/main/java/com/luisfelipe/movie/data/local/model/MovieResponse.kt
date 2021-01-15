package com.luisfelipe.movie.data.local.model

data class MovieResponse(
    val id: String,
    val title: String,
    val vote_count: Int,
    val popularity: Int,
    val backdrop_path: String
)
