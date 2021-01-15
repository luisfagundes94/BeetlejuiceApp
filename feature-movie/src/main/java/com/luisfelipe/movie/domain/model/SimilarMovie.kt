package com.luisfelipe.movie.domain.model

data class SimilarMovie(
    val title: String,
    val release_date: String,
    val genres: List<String>
)