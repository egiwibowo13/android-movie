package id.co.egiwibowo.core.data.source.remote.reponse

import com.google.gson.annotations.SerializedName
import id.co.egiwibowo.core.domain.models.MovieItem
import java.util.*

data class MovieItemDTOResponse(
    val id: Long?,
    val title: String?,

    @SerializedName("poster_path")
    val posterPath: String?
)

fun MovieItemDTOResponse.toDomain(): MovieItem {
    return MovieItem(
        id = id ?: 0,
        title = title ?: "",
        posterPath = posterPath ?: ""
    )
}
