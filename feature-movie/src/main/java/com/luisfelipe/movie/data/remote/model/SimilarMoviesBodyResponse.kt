package com.luisfelipe.movie.data.remote.model

data class SimilarMoviesBodyResponse(
    val page: Int,
    val results: List<SimilarMovieResponse>
)