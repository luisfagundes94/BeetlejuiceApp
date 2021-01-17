package com.luisfelipe.movie.domain.model

data class SimilarMovie(
    val title: String,
    val releaseDate: String?,
    val poster: String,
    val genreIds: List<Int>
) {

    private companion object {
        const val FIRST_NUMBERS_OF_YEAR = 4
    }

    fun getFormattedYear(): String {
        return this.releaseDate?.take(FIRST_NUMBERS_OF_YEAR) ?: ""
    }
}