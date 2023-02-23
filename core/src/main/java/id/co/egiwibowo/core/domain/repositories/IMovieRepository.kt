package id.co.egiwibowo.core.domain.repositories

import androidx.paging.PagingData
import id.co.egiwibowo.core.domain.models.Movie
import id.co.egiwibowo.core.domain.models.MovieItem
import id.co.egiwibowo.core.domain.models.TypeMovies
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getNowPlayingMovies(page: Int): Flow<List<MovieItem>>

    fun getUpcomingMovies(page: Int): Flow<List<MovieItem>>

    fun getPopularMovies(page: Int): Flow<List<MovieItem>>

    fun getTopRatedMovies(page: Int): Flow<List<MovieItem>>

    fun getRecommendationMovies(movieId: Long): Flow<List<MovieItem>>

    fun getPagingMovies(type: TypeMovies): Flow<PagingData<MovieItem>>

    fun getMovie(movieId: Long): Flow<Movie>

    fun getPagingFavoriteMovies(): Flow<PagingData<MovieItem>>

    suspend fun setFavoriteMovie(movie: Movie, state: Boolean)

    suspend fun checkIsFavorite(movieId: Long): Boolean
}