package id.co.egiwibowo.core.data.source.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.co.egiwibowo.core.domain.models.Genre
import id.co.egiwibowo.core.domain.models.Movie
import id.co.egiwibowo.core.domain.models.MovieItem

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val genres: List<Genre>
)

fun MovieEntity.toDomainItem(): MovieItem {
    return MovieItem(
        id = id,
        title = title,
        posterPath = posterPath,
    )
}


fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        backdropPath = backdropPath,
        genres = genres
    )
}
