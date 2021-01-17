package com.luisfelipe.movie.data.mapper

import com.luisfelipe.movie.data.local.model.GenreData
import com.luisfelipe.movie.data.remote.model.GenreResponse
import com.luisfelipe.movie.domain.model.Genre

class GenreMapper {
    companion object {

        fun mapResponseToDomain(genreResponseList: List<GenreResponse>): List<Genre> {
            val genres = mutableListOf<Genre>()
            genreResponseList.map { genreResponse ->
                val genre = Genre(
                    id = genreResponse.id,
                    name = genreResponse.name
                )
                genres.add(genre)
            }
            return genres
        }

        fun mapDataToDomain(genreDataList: List<GenreData>): List<Genre> {
            val genres = mutableListOf<Genre>()
            genreDataList.map { genreData ->
                val genre = Genre(
                    id = genreData.id,
                    name = genreData.name
                )
                genres.add(genre)
            }
            return genres
        }

        fun mapDomainToData(genreList: List<Genre>): List<GenreData> {
            val genreDataList = mutableListOf<GenreData>()
            genreList.map { genre ->
                val genreData = GenreData(
                    id = genre.id,
                    name = genre.name ?: ""
                )
                genreDataList.add(genreData)
            }
            return genreDataList
        }
    }
}
