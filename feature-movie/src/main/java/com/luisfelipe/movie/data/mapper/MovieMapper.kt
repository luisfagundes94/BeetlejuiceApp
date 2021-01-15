package com.luisfelipe.movie.data.mapper

import com.luisfelipe.movie.data.remote.model.MovieResponse
import com.luisfelipe.movie.domain.model.Movie

class MovieMapper {
    companion object {
        const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w500"

        fun mapResponseToDomain(movieResponse: MovieResponse): Movie {
            return Movie(
                id = movieResponse.id,
                title = movieResponse.title,
                backdrop = BASE_BACKDROP_PATH + movieResponse.backdrop_path,
                views = movieResponse.popularity,
                likes = movieResponse.vote_count
            )
        }
    }
}