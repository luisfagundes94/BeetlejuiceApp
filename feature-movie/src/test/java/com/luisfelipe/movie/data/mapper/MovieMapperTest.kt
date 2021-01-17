package com.luisfelipe.movie.data.mapper

import com.luisfelipe.movie.domain.model.Genre
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieMapperTest {

    private val fakeGenreList = listOf(
        Genre(1, "Comedia"),
        Genre(2, "Terror")
    )

    private val fakeGenreIds = listOf(1, 2)

    @Test
    fun `should return genre names given genre ids`() {
        // Act
        val result = MovieMapper.getGenreNamesFromIds(fakeGenreList, fakeGenreIds)

        // Assert
        val expectedValue = listOf("Comedia", "Terror")
        assert(result == expectedValue)
    }

}