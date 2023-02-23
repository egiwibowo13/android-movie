package id.co.egiwibowo.core.data.source.remote.reponse

import com.google.gson.annotations.SerializedName
import id.co.egiwibowo.core.data.source.local.entities.MovieEntity
import id.co.egiwibowo.core.domain.models.Movie
import java.util.*

data class MovieDTOResponse(
    val id: Long?,
    val title: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    val overview: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val genres: List<GenreDTOResponse>?,
    @SerializedName("release_date")
    val releaseDate: String?
)

fun MovieDTOResponse.toDomain(): Movie {
    return Movie(
        id = id ?: 0,
        title = title ?: "",
        overview = overview ?: "",
        releaseDate = releaseDate ?: "",
        voteAverage = 0,
        voteCount = 0,
        posterPath = posterPath ?: "",
        genres = genres?.map(GenreDTOResponse::toDomain) ?: emptyList(),
        backdropPath = backdropPath ?: ""
    )
}
