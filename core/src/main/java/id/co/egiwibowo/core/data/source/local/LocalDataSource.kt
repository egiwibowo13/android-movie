package id.co.egiwibowo.core.data.source.local

import androidx.paging.PagingSource
import id.co.egiwibowo.core.data.source.local.entities.MovieEntity
import id.co.egiwibowo.core.data.source.local.room.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val dao: MovieDao) {

    fun getMovie(movieId: Long): Flow<MovieEntity> {
        return flow {
            try {
                val movie = dao.getMovie(movieId = movieId)
                movie?.let {
                    emit(it)
                }
            } catch (e: Exception) {
                throw e
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getPagingMovies(): PagingSource<Int, MovieEntity> {
        return dao.pagingSource()
    }

    suspend fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        if (newState) {
            dao.insertMovie(movie)
        } else {
            dao.deleteMovie(movie)
        }
    }

    suspend fun checkIsFavoriteMovie(movieId: Long): Boolean {
        return dao.getMovie(movieId) != null
    }
}