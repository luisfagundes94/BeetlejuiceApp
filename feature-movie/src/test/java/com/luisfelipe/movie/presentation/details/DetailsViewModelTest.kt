package com.luisfelipe.movie.presentation.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.luisfelipe.extensions.getOrAwaitValue
import com.luisfelipe.movie.domain.enums.ResultStatus
import com.luisfelipe.movie.domain.model.Movie
import com.luisfelipe.movie.domain.model.SimilarMovie
import com.luisfelipe.movie.domain.usecase.*
import com.luisfelipe.utils.CoroutineRule
import com.luisfelipe.utils.FakeDataSource.BACKDROP
import com.luisfelipe.utils.FakeDataSource.ERROR_MESSAGE
import com.luisfelipe.utils.FakeDataSource.MOVIE_ID
import com.luisfelipe.utils.FakeDataSource.LIKES
import com.luisfelipe.utils.FakeDataSource.TITLE
import com.luisfelipe.utils.FakeDataSource.VIEWS
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DetailsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val getMovieDetailsFromApi: GetMovieDetailsFromApi = mockk()
    private val getSimilarMoviesFromApi: GetSimilarMoviesFromApi = mockk()
    private val getMovieGenresFromApi: GetMovieGenresFromApi = mockk()
    private val getIsFavoriteMovieFromCache: GetIsFavoriteMovieFromCache = mockk()
    private val setIsFavoriteMovieFromCache: SetIsFavoriteMovieToCache = mockk()

    private val viewModel = spyk(
        DetailsViewModel(
            getMovieDetailsFromApi,
            getSimilarMoviesFromApi,
            getMovieGenresFromApi,
            getIsFavoriteMovieFromCache,
            setIsFavoriteMovieFromCache,
        )
    )

    private val fakeMovie = Movie(
        id = MOVIE_ID,
        title = TITLE,
        backdrop = BACKDROP,
        views = VIEWS,
        likes = LIKES
    )

    private val fakeSimilarMovieList = listOf(
        SimilarMovie(
            title = TITLE,
            releaseDate = "2020-09-10",
            poster = "//////////",
            genreIds = listOf(1, 2, 3, 4)
        )
    )

    @Test
    fun `should return movie details when getMovieDetails is successful`() {
        // Arrange
        coEvery { getMovieDetailsFromApi(MOVIE_ID) } returns ResultStatus.Success(fakeMovie)

        // Act
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.getMovieDetails()
        }

        // Assert
        val expectedValue = viewModel.movieDetailsResultStatus.getOrAwaitValue()
        assert(expectedValue == ResultStatus.Success(fakeMovie))
    }

    @Test
    fun `should return an error message when getMovieDetails is unsuccessful`() {
        // Arrange
        coEvery { getMovieDetailsFromApi(MOVIE_ID) } returns ResultStatus.Error(ERROR_MESSAGE)

        // Act
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.getMovieDetails()
        }

        // Assert
        val expectedValue = viewModel.movieDetailsResultStatus.getOrAwaitValue()
        assert(expectedValue == ResultStatus.Error(ERROR_MESSAGE))
    }

    @Test
    fun `should return a list of similar movies when getSimilarMovies is successful`() {
        // Arrange
        coEvery { getSimilarMoviesFromApi(MOVIE_ID, pageNumber = 1) } returns ResultStatus.Success(
            fakeSimilarMovieList
        )

        // Act
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.getSimilarMovies()
        }

        // Assert
        val expectedValue = viewModel.similarMoviesResultStatus.getOrAwaitValue()
        assert(expectedValue == ResultStatus.Success(fakeSimilarMovieList))
    }

    @Test
    fun `should return an error message when getSimilarMovies is unsuccessful`() {
        // Arrange
        coEvery { getSimilarMoviesFromApi(MOVIE_ID, pageNumber = 1) } returns ResultStatus.Error(
            ERROR_MESSAGE
        )

        // Act
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.getSimilarMovies()
        }

        // Assert
        val expectedValue = viewModel.similarMoviesResultStatus.getOrAwaitValue()
        assert(expectedValue == ResultStatus.Error(ERROR_MESSAGE))
    }

    @Test
    fun `should set isFavoriteMovie to true when default value is false`() {
        // Arrange
        coEvery { getIsFavoriteMovieFromCache(MOVIE_ID.toString()) } returns false
        coEvery { setIsFavoriteMovieFromCache(MOVIE_ID.toString(), true) } just runs

        // Act
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.updateFavoriteIconState(MOVIE_ID)
        }

        // Assert
        val expectedValue = viewModel.isFavoriteMovie.getOrAwaitValue()
        assert(expectedValue)
    }

    @Test
    fun `should set isFavoriteMovie to false when default value is true`() {
        // Arrange
        coEvery { getIsFavoriteMovieFromCache(MOVIE_ID.toString()) } returns true
        coEvery { setIsFavoriteMovieFromCache(MOVIE_ID.toString(), false) } just runs

        // Act
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.updateFavoriteIconState(MOVIE_ID)
        }

        // Assert
        val expectedValue = viewModel.isFavoriteMovie.getOrAwaitValue()
        assert(!expectedValue)
    }

}