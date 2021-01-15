package com.luisfelipe.movie.domain.model

import kotlin.math.ln
import kotlin.math.pow

data class Movie(
    val id: Int,
    val title: String,
    val likes: Int,
    val views: Double,
    val backdrop: String
) {
    fun getFormattedLikes(): String {
        if (this.likes < 1000) return "" + likes
        val exp = (ln(this.likes.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f%c", this.likes / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
    }
}
