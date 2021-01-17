package com.luisfelipe.movie.data.mapper

import com.luisfelipe.movie.data.remote.model.MovieResponse
import com.luisfelipe.movie.data.remote.model.SimilarMovieResponse
import com.luisfelipe.movie.domain.model.Genre
import com.luisfelipe.movie.domain.model.Movie
import com.luisfelipe.movie.domain.model.SimilarMovie

class MovieMapper {
    companion object {
        private const val BASE_IMAGE_PATH = "https://image.tmdb.org/t/p/w500"

        fun mapResponseToDomain(movieResponse: MovieResponse): Movie {
            return Movie(
                id = movieResponse.id,
                title = movieResponse.title,
                backdrop = BASE_IMAGE_PATH + movieResponse.backdropPath,
                views = movieResponse.popularity,
                likes = movieResponse.voteCount
            )
        }

        fun mapResponseToDomain(
            similarMoviesResponse: List<SimilarMovieResponse>,
            genres: List<Genre>
        ): List<SimilarMovie> {
            val similarMovies = mutableListOf<SimilarMovie>()
            similarMoviesResponse.map { similarMovieResponse ->
                val similarMovie = SimilarMovie(
                    title = similarMovieResponse.title,
                    releaseDate = similarMovieResponse.releaseDate,
                    poster = BASE_IMAGE_PATH + similarMovieResponse.posterPath,
                    genreNames = getGenreNamesFromIds(genres, similarMovieResponse.genreIds)
                )
                similarMovies.add(similarMovie)
            }
            return similarMovies
        }

        private fun getGenreNamesFromIds(genres: List<Genre>, genreIds: List<Int>): List<String> {
            val filteredGenres = genres.filter { genre -> genreIds.contains(genre.id)}
            return filteredGenres.map { it.name }
        }
    }
}