package id.co.egiwibowo.core.domain.models

import id.co.egiwibowo.core.config.Config

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Int,
    val voteCount: Int,
    val posterPath: String,
    val backdropPath: String,
    val genres: List<Genre>
) {
    val posterUrl = "${Config.URL_IMAGE}${posterPath}"
    val backdropUrl = "${Config.URL_IMAGE}${backdropPath}"
}