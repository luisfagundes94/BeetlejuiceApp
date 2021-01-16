package com.luisfelipe.movie.data.remote.model

data class SimilarMovieResponse(
    val title: String,
    val release_date: String,
    val poster_path: String,
    val genre_ids: List<Int>
)
