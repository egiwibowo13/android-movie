package id.co.egiwibowo.core.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import id.co.egiwibowo.core.data.source.local.LocalDataSource
import id.co.egiwibowo.core.data.source.local.entities.MovieEntity
import id.co.egiwibowo.core.data.source.local.entities.toDomainItem
import id.co.egiwibowo.core.data.source.local.entities.toEntity
import id.co.egiwibowo.core.data.source.remote.MoviesPagingSource
import id.co.egiwibowo.core.data.source.remote.RemoteDataSource
import id.co.egiwibowo.core.data.source.remote.network.ApiService
import id.co.egiwibowo.core.data.source.remote.reponse.MovieDTOResponse
import id.co.egiwibowo.core.data.source.remote.reponse.MovieItemDTOResponse
import id.co.egiwibowo.core.data.source.remote.reponse.toDomain
import id.co.egiwibowo.core.domain.models.Movie
import id.co.egiwibowo.core.domain.models.MovieItem
import id.co.egiwibowo.core.domain.models.TypeMovies
import id.co.egiwibowo.core.domain.repositories.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource,
    private val service: ApiService,
): IMovieRepository {
    override fun getNowPlayingMovies(page: Int): Flow<List<MovieItem>> {
        return remote.getNowPlayingMovies(page).map { it.map(MovieItemDTOResponse::toDomain) }
    }

    override fun getUpcomingMovies(page: Int): Flow<List<MovieItem>> {
        return remote.getUpcomingMovies(page).map { it.map(MovieItemDTOResponse::toDomain) }
    }

    override fun getPopularMovies(page: Int): Flow<List<MovieItem>> {
        return remote.getPopularMovies(page).map { it.map(MovieItemDTOResponse::toDomain) }
    }

    override fun getTopRatedMovies(page: Int): Flow<List<MovieItem>> {
        return remote.getTopRatedMovies(page).map { it.map(MovieItemDTOResponse::toDomain) }
    }

    override fun getRecommendationMovies(movieId: Long): Flow<List<MovieItem>> {
        Log.d("getRecommendationMovies", movieId.toString())
        return remote.getRecommendation(movieId = movieId).map { it.map(MovieItemDTOResponse::toDomain) }
    }

    override fun getPagingMovies(type: TypeMovies): Flow<PagingData<MovieItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                MoviesPagingSource(service, type)
            }
        ).flow.map { paging ->
            paging.map(MovieItemDTOResponse::toDomain)
        }
    }

    override fun getMovie(movieId: Long): Flow<Movie> {
        return remote.getMovie(movieId = movieId).map(MovieDTOResponse::toDomain)
    }

    override fun getPagingFavoriteMovies(): Flow<PagingData<MovieItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 50
            ),
            pagingSourceFactory = {
                local.getPagingMovies()
            }
        ).flow.map { paging ->
            paging.map(MovieEntity::toDomainItem)
        }
    }

    override suspend fun setFavoriteMovie(movie: Movie, state: Boolean) {
        local.setFavoriteMovie(movie.toEntity(), state)
    }

    override suspend fun checkIsFavorite(movieId: Long): Boolean {
        return local.checkIsFavoriteMovie(movieId = movieId)
    }
}