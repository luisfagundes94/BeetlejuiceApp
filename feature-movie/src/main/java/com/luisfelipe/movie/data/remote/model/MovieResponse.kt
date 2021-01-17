package com.luisfelipe.movie.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val id: Int,
    val title: String,
    val popularity: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("backdrop_path") val backdropPath: String
)
