package com.luisfelipe.movie.domain.model

import com.luisfelipe.utils.FakeDataSource.BACKDROP
import com.luisfelipe.utils.FakeDataSource.MOVIE_ID
import com.luisfelipe.utils.FakeDataSource.TITLE
import com.luisfelipe.utils.FakeDataSource.VIEWS
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class MovieTest {

    @Test
    fun `should return formatted number of views given a decimal number`() {
        val fakeMovie = Movie(
            id = MOVIE_ID,
            title = TITLE,
            likes = 1_456_653,
            views = VIEWS,
            backdrop = BACKDROP
        )

        val expectedValue = "1.5M"
        assert(fakeMovie.getFormattedLikes() == expectedValue)
    }

    @Test
    fun `should return formatted number of views given a number greater then 1_000`() {
        val fakeMovie = Movie(
            id = MOVIE_ID,
            title = TITLE,
            likes = 4000,
            views = VIEWS,
            backdrop = BACKDROP
        )

        val expectedValue = "4.0k"

        assert(fakeMovie.getFormattedLikes() == expectedValue)
    }

    @Test
    fun `should return formatted number of views given a number greater then 10_000`() {
        val fakeMovie = Movie(
            id = MOVIE_ID,
            title = TITLE,
            likes = 12_000,
            views = VIEWS,
            backdrop = BACKDROP
        )
        val expectedValue = "12.0k"

        assert(fakeMovie.getFormattedLikes() == expectedValue)
    }

    @Test
    fun `should return formatted number of views given a number greater then 100_000`() {
        val fakeMovie = Movie(
            id = MOVIE_ID,
            title = TITLE,
            likes = 120_000,
            views = VIEWS,
            backdrop = BACKDROP
        )
        val expectedValue = "120.0k"

        assert(fakeMovie.getFormattedLikes() == expectedValue)
    }

    @Test
    fun `should return formatted number of views given a number greater then 100_000_000`() {
        val fakeMovie = Movie(
            id = MOVIE_ID,
            title = TITLE,
            likes = 120_000_000,
            views = VIEWS,
            backdrop = BACKDROP
        )
        val expectedValue = "120.0M"

        assert(fakeMovie.getFormattedLikes() == expectedValue)
    }
}
