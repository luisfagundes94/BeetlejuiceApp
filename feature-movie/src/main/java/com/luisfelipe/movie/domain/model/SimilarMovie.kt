package com.luisfelipe.movie.domain.model

data class SimilarMovie(
    val title: String,
    val releaseDate: String,
    val poster: String,
) {
    fun getFormattedYear(): String {
        return this.releaseDate.substring(0, 4)
    }
}