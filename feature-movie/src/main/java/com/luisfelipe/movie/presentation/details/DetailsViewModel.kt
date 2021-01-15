package com.luisfelipe.movie.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfelipe.movie.domain.enums.ResultStatus
import com.luisfelipe.movie.domain.model.Movie
import com.luisfelipe.movie.domain.model.SimilarMovie
import com.luisfelipe.movie.domain.usecase.GetMovieDetailsFromApi
import com.luisfelipe.movie.domain.usecase.GetSimilarMoviesFromApi
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getMovieDetailsFromApi: GetMovieDetailsFromApi,
    private val getSimilarMoviesFromApi: GetSimilarMoviesFromApi,
) : ViewModel() {

    private val movieDetailsResultStatusLiveData = MutableLiveData<ResultStatus<Movie>>()
    val movieDetailsResultStatus: LiveData<ResultStatus<Movie>> = movieDetailsResultStatusLiveData

    private val similarMoviesResultStatusLiveData =
        MutableLiveData<ResultStatus<List<SimilarMovie>>>()
    val similarMoviesResultStatus = similarMoviesResultStatusLiveData

    private companion object {
        const val BEETLEJUICE_ID = 4011
    }

    fun getMovieDetails() = viewModelScope.launch {
        val movieDetailsResultStatus = getMovieDetailsFromApi(BEETLEJUICE_ID)
        movieDetailsResultStatusLiveData.postValue(movieDetailsResultStatus)
    }

    fun getSimilarMovies() = viewModelScope.launch {
        val similarMoviesResultStatus = getSimilarMoviesFromApi(BEETLEJUICE_ID)
        similarMoviesResultStatusLiveData.postValue(similarMoviesResultStatus)
    }
}