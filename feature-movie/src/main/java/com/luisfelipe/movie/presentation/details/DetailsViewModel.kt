package com.luisfelipe.movie.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfelipe.movie.domain.enums.ResultStatus
import com.luisfelipe.movie.domain.model.Movie
import com.luisfelipe.movie.domain.usecase.GetMovieDetailsFromApi
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getMovieDetailsFromApi: GetMovieDetailsFromApi
): ViewModel() {

    private val movieDetailsResultStatusLiveData = MutableLiveData<ResultStatus<Movie>>()
    val movieDetailsResultStatus: LiveData<ResultStatus<Movie>> = movieDetailsResultStatusLiveData

    private companion object {
        const val BEETLEJUICE_ID = 4011
    }

    fun getMovieDetails() = viewModelScope.launch {
        val movieDetailsResultStatus = getMovieDetailsFromApi(BEETLEJUICE_ID)
        movieDetailsResultStatusLiveData.postValue(movieDetailsResultStatus)
    }
}