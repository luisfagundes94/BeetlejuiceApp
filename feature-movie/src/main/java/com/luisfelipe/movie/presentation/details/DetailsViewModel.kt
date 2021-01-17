package com.luisfelipe.movie.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.luisfelipe.movie.domain.enums.ResultStatus
import com.luisfelipe.movie.domain.model.Genre
import com.luisfelipe.movie.domain.model.Movie
import com.luisfelipe.movie.domain.model.SimilarMovie
import com.luisfelipe.movie.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getMovieDetailsFromApi: GetMovieDetailsFromApi,
    private val getSimilarMoviesFromApi: GetSimilarMoviesFromApi,
    private val getMovieGenresFromApi: GetMovieGenresFromApi,
    private val getIsFavoriteMovieFromCache: GetIsFavoriteMovieFromCache,
    private val setIsFavoriteMovieToCache: SetIsFavoriteMovieToCache
) : ViewModel() {

    private val _movieDetailsResultStatus = MutableLiveData<ResultStatus<Movie>>()
    val movieDetailsResultStatus: LiveData<ResultStatus<Movie>> = _movieDetailsResultStatus

    private val _similarMoviesResultStatus =
        MutableLiveData<ResultStatus<List<SimilarMovie>>>()
    val similarMoviesResultStatus = _similarMoviesResultStatus

    private val _movieGenresResultStatus = MutableLiveData<ResultStatus<List<Genre>>>()
    val movieGenresResultStatus: LiveData<ResultStatus<List<Genre>>> = _movieGenresResultStatus

    private val _isFavoriteMovie = MutableLiveData<Boolean>()
    val isFavoriteMovie: LiveData<Boolean> = _isFavoriteMovie

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var isSimilarMovieListLoading = false
    private var pageNumber = 1
    private val pageLimit = 10

    private companion object {
        const val BEETLEJUICE_MOVIE_ID = 4011
    }

    fun getMovieDetails() = viewModelScope.launch(Dispatchers.IO) {
        _isLoading.postValue(true)
        val movieDetailsResultStatus = getMovieDetailsFromApi(BEETLEJUICE_MOVIE_ID)
        _movieDetailsResultStatus.postValue(movieDetailsResultStatus)
        _isLoading.postValue(false)
    }

    fun getSimilarMovies(page: Int = pageNumber) = viewModelScope.launch(Dispatchers.IO) {
        isSimilarMovieListLoading = true
        val similarMoviesResultStatus = getSimilarMoviesFromApi(BEETLEJUICE_MOVIE_ID, page)
        _similarMoviesResultStatus.postValue(similarMoviesResultStatus)
        isSimilarMovieListLoading = false
    }

    fun getMovieGenres() = viewModelScope.launch(Dispatchers.IO) {
        val movieGenresResultStatus = getMovieGenresFromApi()
        _movieGenresResultStatus.postValue(movieGenresResultStatus)
    }

    fun getGenreNamesFromIds(genres: List<Genre>, genreIds: List<Int>): List<String> {
        val filteredGenres = genres.filter { genre -> genreIds.contains(genre.id) }
        return filteredGenres.map { it.name }
    }

    fun updateInitialFavoriteIconState(movieId: Int) = viewModelScope.launch {
        val isFavorite = getIsFavoriteMovieFromCache(movieId.toString())
        if (isFavorite) _isFavoriteMovie.postValue(true)
    }

    fun updateFavoriteIconState(movieId: Int) = viewModelScope.launch {
        val movieIdString = movieId.toString()
        val isAlreadyFavorite = getIsFavoriteMovieFromCache(movieIdString)
        if (isAlreadyFavorite) {
            _isFavoriteMovie.postValue(false)
            setIsFavoriteMovieToCache(movieIdString, false)
        } else {
            _isFavoriteMovie.postValue(true)
            setIsFavoriteMovieToCache(movieIdString, true)
        }
    }

    fun onRecyclerViewScrolled(dy: Int, layoutManager: LinearLayoutManager) {
        if (dy > 0) {
            val hasUserReachedTheEnd = hasUserReachedTheEnd(layoutManager)
            if (isSimilarMovieListLoading.not() && hasUserReachedTheEnd && pageNumber < pageLimit) {
                pageNumber++
                getSimilarMovies(pageNumber)
            }
        }
    }

    private fun hasUserReachedTheEnd(layoutManager: LinearLayoutManager): Boolean {
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
        return visibleItemCount + pastVisibleItem >= totalItemCount
    }

}