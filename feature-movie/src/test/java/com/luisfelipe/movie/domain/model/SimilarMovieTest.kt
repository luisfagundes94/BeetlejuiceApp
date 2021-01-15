package com.luisfelipe.movie.domain.model

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SimilarMovieTest {

    @Test
    fun `should return year given a release date`() {
        val fakeSimilarMovie = SimilarMovie(
            title = "Beetlejuice",
            releaseDate = "2020-09-10",
            poster = "/////////////"
        )

        val expectedValue = "2020"
        assert(fakeSimilarMovie.getFormattedYear() == expectedValue)
    }
}