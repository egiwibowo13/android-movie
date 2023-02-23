package id.co.egiwibowo.core.domain.models

import id.co.egiwibowo.core.config.Config

data class MovieItem(
    val id: Long,
    val title: String,
    val posterPath: String,
) {
    val posterUrl = "${Config.URL_IMAGE}${posterPath}"
}