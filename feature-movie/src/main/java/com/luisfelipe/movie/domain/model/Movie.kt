package com.luisfelipe.movie.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val likes: Int,
    val views: Double,
    val backdrop: String
)
