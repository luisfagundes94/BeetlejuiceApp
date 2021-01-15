package com.luisfelipe.movie.data.remote.mapper

import com.luisfelipe.movie.data.remote.model.MovieResponse
import com.luisfelipe.movie.domain.model.Movie

class MovieMapper {
    companion object {
        fun mapResponseToDomain(movieResponse: MovieResponse): Movie {
            return Movie(
                id = movieResponse.id,
                title = movieResponse.title,
                backdrop = movieResponse.backdrop_path,
                views = movieResponse.popularity,
                likes = movieResponse.vote_count
            )
        }
    }
}