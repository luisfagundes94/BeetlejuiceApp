package com.luisfelipe.movie.data.mapper

import com.luisfelipe.movie.data.remote.model.GenreResponse
import com.luisfelipe.movie.domain.model.Genre

class GenreMapper {
    companion object {

        fun mapResponseToDomain(genreResponseList: List<GenreResponse>): List<Genre> {
            val genres = mutableListOf<Genre>()
            for (genreResponse in genreResponseList) {
                val genre = Genre(
                    id = genreResponse.id,
                    name = genreResponse.name
                )
                genres.add(genre)
            }
            return genres
        }
    }
}