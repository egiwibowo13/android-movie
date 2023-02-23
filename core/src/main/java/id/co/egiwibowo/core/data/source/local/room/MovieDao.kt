package id.co.egiwibowo.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import id.co.egiwibowo.core.data.source.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie WHERE id = :movieId")
    suspend fun getMovie(movieId: Long): MovieEntity?

    @Query("SELECT * FROM movie")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

}