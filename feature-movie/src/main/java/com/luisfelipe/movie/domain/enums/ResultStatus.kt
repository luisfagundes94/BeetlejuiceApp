package com.luisfelipe.movie.domain.enums

sealed class ResultStatus<out T: Any> {
    data class Success<out T: Any>(val data: T) : ResultStatus<T>()
    data class Error(val exception: String) : ResultStatus<Nothing>()
    object InProgress : ResultStatus<Nothing>()
}