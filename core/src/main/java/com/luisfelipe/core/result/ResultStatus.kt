package com.luisfelipe.core.result

sealed class ResultStatus<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultStatus<T>()
    data class Error(val exception: String) : ResultStatus<Nothing>()
}

