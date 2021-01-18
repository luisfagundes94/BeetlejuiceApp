package com.luisfelipe.movie.presentation.details

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfelipe.movie.domain.enums.ResultStatus
import com.luisfelipe.movie.domain.model.Movie
import com.luisfelipe.movie.domain.model.SimilarMovie
import com.luisfelipe.movie.domain.usecase.GetIsFavoriteMovieFromCache
import com.luisfelipe.movie.domain.usecase.GetMovieDetailsFromApi
import com.luisfelipe.movie.domain.usecase.GetSimilarMoviesFromApi
import com.luisfelipe.movie.domain.usecase.SetIsFavoriteMovieToCache
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getMovieDetailsFromApi: GetMovieDetailsFromApi,
    private val getSimilarMoviesFromApi: GetSimilarMoviesFromApi,
    private val getIsFavoriteMovieFromCache: GetIsFavoriteMovieFromCache,
    private val setIsFavoriteMovieToCache: SetIsFavoriteMovieToCache,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private companion object {
        const val BEETLEJUICE_MOVIE_ID = 4011
    }

    private val _movieDetailsResultStatus = MutableLiveData<ResultStatus<Movie>>()
    val movieDetailsResultStatus: LiveData<ResultStatus<Movie>> = _movieDetailsResultStatus

    private val _similarMoviesResultStatus =
        MutableLiveData<ResultStatus<List<SimilarMovie>>>()
    val similarMoviesResultStatus = _similarMoviesResultStatus

    private val _isFavoriteMovie = MutableLiveData<Boolean>()
    val isFavoriteMovie: LiveData<Boolean> = _isFavoriteMovie

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    @VisibleForTesting
    internal var isSimilarMovieListLoading = false

    fun getMovieDetails() = viewModelScope.launch(coroutineDispatcher) {
        _isLoading.postValue(true)
        val movieDetailsResultStatus = getMovieDetailsFromApi(BEETLEJUICE_MOVIE_ID)
        _movieDetailsResultStatus.postValue(movieDetailsResultStatus)
        _isLoading.postValue(false)
    }

    fun getSimilarMovies() = viewModelScope.launch(coroutineDispatcher) {
        isSimilarMovieListLoading = true
        val similarMoviesResultStatus = getSimilarMoviesFromApi(BEETLEJUICE_MOVIE_ID)
        _similarMoviesResultStatus.postValue(similarMoviesResultStatus)
        isSimilarMovieListLoading = false
    }

    fun updateInitialFavoriteIconState(movieId: Int) = viewModelScope.launch {
        val isFavorite = getIsFavoriteMovieFromCache(movieId.toString())
        if (isFavorite) _isFavoriteMovie.postValue(true)
    }

    fun updateFavoriteIconState(movieId: Int) = viewModelScope.launch {
        val movieIdString = movieId.toString()
        val isAlreadyFavorite = getIsFavoriteMovieFromCache(movieIdString)
        if (isAlreadyFavorite) updateFavoriteMovie(movieIdString, false)
        else updateFavoriteMovie(movieIdString, true)
    }

    private suspend fun updateFavoriteMovie(movieIdString: String, favorite: Boolean) {
        _isFavoriteMovie.postValue(favorite)
        setIsFavoriteMovieToCache(movieIdString, favorite)
    }

    internal fun requestNextPage() {
        if (isSimilarMovieListLoading.not()) getSimilarMovies()
    }
}
