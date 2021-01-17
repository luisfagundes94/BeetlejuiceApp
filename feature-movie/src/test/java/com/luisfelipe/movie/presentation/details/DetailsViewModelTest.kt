package com.luisfelipe.movie.presentation.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.luisfelipe.extensions.getOrAwaitValue
import com.luisfelipe.movie.domain.enums.ResultStatus
import com.luisfelipe.movie.domain.model.Movie
import com.luisfelipe.movie.domain.model.SimilarMovie
import com.luisfelipe.movie.domain.usecase.GetIsFavoriteMovieFromCache
import com.luisfelipe.movie.domain.usecase.GetMovieDetailsFromApi
import com.luisfelipe.movie.domain.usecase.GetSimilarMoviesFromApi
import com.luisfelipe.movie.domain.usecase.SetIsFavoriteMovieToCache
import com.luisfelipe.utils.CoroutineRule
import com.luisfelipe.utils.FakeDataSource.BACKDROP
import com.luisfelipe.utils.FakeDataSource.ERROR_MESSAGE
import com.luisfelipe.utils.FakeDataSource.LIKES
import com.luisfelipe.utils.FakeDataSource.MOVIE_ID
import com.luisfelipe.utils.FakeDataSource.TITLE
import com.luisfelipe.utils.FakeDataSource.VIEWS
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    private val getIsFavoriteMovieFromCache: GetIsFavoriteMovieFromCache = mockk()
    private val setIsFavoriteMovieFromCache: SetIsFavoriteMovieToCache = mockk()

    private val viewModel = spyk(
        DetailsViewModel(
            getMovieDetailsFromApi,
            getSimilarMoviesFromApi,
            getIsFavoriteMovieFromCache,
            setIsFavoriteMovieFromCache,
            coroutinesTestRule.testDispatcher
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
            genreNames = listOf("Comédia", "Terror")
        )
    )

    @Test
    fun `should return movie details when getMovieDetails is successful`() {
        // Arrange
        coEvery { getMovieDetailsFromApi(MOVIE_ID) } returns ResultStatus.Success(fakeMovie)

        // Act
        viewModel.getMovieDetails()

        // Assert
        val expectedValue = viewModel.movieDetailsResultStatus.getOrAwaitValue()
        assert(expectedValue == ResultStatus.Success(fakeMovie))
    }

    @Test
    fun `should return an error message when getMovieDetails is unsuccessful`() {
        // Arrange
        coEvery { getMovieDetailsFromApi(MOVIE_ID) } returns ResultStatus.Error(ERROR_MESSAGE)

        // Act
        viewModel.getMovieDetails()

        // Assert
        val expectedValue = viewModel.movieDetailsResultStatus.getOrAwaitValue()
        assert(expectedValue == ResultStatus.Error(ERROR_MESSAGE))
    }

    @Test
    fun `should return a list of similar movies when getSimilarMovies is successful`() {
        // Arrange
        coEvery { getSimilarMoviesFromApi(MOVIE_ID) } returns ResultStatus.Success(
            fakeSimilarMovieList
        )

        // Act
        viewModel.getSimilarMovies()

        // Assert
        val expectedValue = viewModel.similarMoviesResultStatus.getOrAwaitValue()
        assert(expectedValue == ResultStatus.Success(fakeSimilarMovieList))
    }

    @Test
    fun `should return an error message when getSimilarMovies is unsuccessful`() {
        // Arrange
        coEvery { getSimilarMoviesFromApi(MOVIE_ID) } returns ResultStatus.Error(
            ERROR_MESSAGE
        )

        // Act
        viewModel.getSimilarMovies()

        // Assert
        val expectedValue = viewModel.similarMoviesResultStatus.getOrAwaitValue()
        assert(expectedValue == ResultStatus.Error(ERROR_MESSAGE))
    }

    @Test
    fun `should update isFavoriteMovie to true when default value is false`() {
        // Arrange
        coEvery { getIsFavoriteMovieFromCache(MOVIE_ID.toString()) } returns false
        coEvery { setIsFavoriteMovieFromCache(MOVIE_ID.toString(), true) } just runs

        // Act
        viewModel.updateFavoriteIconState(MOVIE_ID)

        // Assert
        val expectedValue = viewModel.isFavoriteMovie.getOrAwaitValue()
        assert(expectedValue)
    }

    @Test
    fun `should update isFavoriteMovie to false when default value is true`() {
        // Arrange
        coEvery { getIsFavoriteMovieFromCache(MOVIE_ID.toString()) } returns true
        coEvery { setIsFavoriteMovieFromCache(MOVIE_ID.toString(), false) } just runs

        // Act
        viewModel.updateFavoriteIconState(MOVIE_ID)

        // Assert
        val expectedValue = viewModel.isFavoriteMovie.getOrAwaitValue()
        assert(!expectedValue)
    }

    @Test
    fun `should not request next page when similar movies is loading`() {
        // Arrange
        viewModel.isSimilarMovieListLoading = true
        // Act
        viewModel.requestNextPage()

        // Assert
        verify(exactly = 0) { viewModel.getSimilarMovies() }
    }

    @Test
    fun `should request next page when similar movies is not loading`() {
        // Arrange
        coEvery { getSimilarMoviesFromApi(MOVIE_ID) } returns ResultStatus.Success(
            fakeSimilarMovieList
        )
        viewModel.isSimilarMovieListLoading = false

        //Act
        viewModel.requestNextPage()

        // Assert
        verify(exactly = 1) { viewModel.getSimilarMovies() }
    }
}
