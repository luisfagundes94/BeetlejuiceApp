package com.luisfelipe.movie.data.remote.model

import com.google.gson.annotations.SerializedName

data class SimilarMovieResponse(
    val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("genre_ids") val genreIds: List<Int>
)
