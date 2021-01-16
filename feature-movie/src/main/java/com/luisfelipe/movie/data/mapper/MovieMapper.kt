package com.luisfelipe.movie.data.mapper

import com.luisfelipe.movie.data.remote.model.MovieResponse
import com.luisfelipe.movie.data.remote.model.SimilarMovieResponse
import com.luisfelipe.movie.domain.model.Movie
import com.luisfelipe.movie.domain.model.SimilarMovie

class MovieMapper {
    companion object {
        private const val BASE_IMAGE_PATH = "https://image.tmdb.org/t/p/w500"

        fun mapResponseToDomain(movieResponse: MovieResponse): Movie {
            return Movie(
                id = movieResponse.id,
                title = movieResponse.title,
                backdrop = BASE_IMAGE_PATH + movieResponse.backdrop_path,
                views = movieResponse.popularity,
                likes = movieResponse.vote_count
            )
        }

        fun mapResponseToDomain(similarMoviesResponse: List<SimilarMovieResponse>): List<SimilarMovie> {
            val similarMovies = mutableListOf<SimilarMovie>()
            for (similarMovieResponse in similarMoviesResponse) {
                val similarMovie = SimilarMovie(
                    title = similarMovieResponse.title,
                    releaseDate = similarMovieResponse.release_date,
                    poster = BASE_IMAGE_PATH + similarMovieResponse.poster_path,
                    genreIds = similarMovieResponse.genre_ids
                )
                similarMovies.add(similarMovie)
            }
            return similarMovies
        }
    }
}