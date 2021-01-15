package com.luisfelipe.movie.presentation.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.luisfelipe.extensions.getOrAwaitValue
import com.luisfelipe.movie.domain.enums.ResultStatus
import com.luisfelipe.movie.domain.model.Movie
import com.luisfelipe.movie.domain.model.SimilarMovie
import com.luisfelipe.movie.domain.usecase.GetMovieDetailsFromApi
import com.luisfelipe.movie.domain.usecase.GetSimilarMoviesFromApi
import com.luisfelipe.utils.CoroutineRule
import com.luisfelipe.utils.FakeDataSource.BACKDROP
import com.luisfelipe.utils.FakeDataSource.ERROR_MESSAGE
import com.luisfelipe.utils.FakeDataSource.MOVIE_ID
import com.luisfelipe.utils.FakeDataSource.LIKES
import com.luisfelipe.utils.FakeDataSource.TITLE
import com.luisfelipe.utils.FakeDataSource.VIEWS
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
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

    private val viewModel = spyk(
        DetailsViewModel(
            getMovieDetailsFromApi,
            getSimilarMoviesFromApi
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
            poster = "//////////"
        )
    )

    @Test
    fun `should return movie details when movieDetailsResultStatus is successful`() {
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
    fun `should return an error message when movieDetailsResultStatus is unsuccessful`() {
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
    fun `should return a list of similar movies when similiarMoviesResultStatus is successful`() {
        // Arrange
        coEvery { getSimilarMoviesFromApi(MOVIE_ID) } returns ResultStatus.Success(
            fakeSimilarMovieList
        )

        // Act
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.getSimilarMovies()
        }

        val expectedValue = viewModel.similarMoviesResultStatus.getOrAwaitValue()
        assert(expectedValue == ResultStatus.Success(fakeSimilarMovieList))
    }

    @Test
    fun `should return an error message when similarMoviesResultStatus is unsuccessful`() {
        // Arrange
        coEvery { getSimilarMoviesFromApi(MOVIE_ID) } returns ResultStatus.Error(ERROR_MESSAGE)

        // Act
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.getSimilarMovies()
        }

        val expectedValue = viewModel.similarMoviesResultStatus.getOrAwaitValue()
        assert(expectedValue == ResultStatus.Error(ERROR_MESSAGE))
    }

}