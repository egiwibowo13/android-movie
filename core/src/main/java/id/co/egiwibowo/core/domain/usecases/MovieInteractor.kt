package id.co.egiwibowo.core.domain.usecases

import androidx.paging.PagingData
import id.co.egiwibowo.core.domain.models.Movie
import id.co.egiwibowo.core.domain.models.MovieItem
import id.co.egiwibowo.core.domain.models.TypeMovies
import id.co.egiwibowo.core.domain.repositories.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository): MovieUseCase {
    override fun getNowPlayingMovies(page: Int): Flow<List<MovieItem>> {
        return movieRepository.getNowPlayingMovies(page)
    }

    override fun getUpcomingMovies(page: Int): Flow<List<MovieItem>> {
        return movieRepository.getUpcomingMovies(page)
    }

    override fun getPopularMovies(page: Int): Flow<List<MovieItem>> {
        return movieRepository.getPopularMovies(page)
    }

    override fun getTopRatedMovies(page: Int): Flow<List<MovieItem>> {
        return movieRepository.getTopRatedMovies(page)
    }

    override fun getRecommendationMovies(movieId: Long): Flow<List<MovieItem>> {
        return movieRepository.getRecommendationMovies(movieId = movieId)
    }

    override fun getPagingMovies(typeMovies: TypeMovies): Flow<PagingData<MovieItem>> {
        return movieRepository.getPagingMovies(typeMovies)
    }

    override fun getMovie(movieId: Long): Flow<Movie> = movieRepository.getMovie(movieId = movieId)

    override fun getPagingFavoriteMovies(): Flow<PagingData<MovieItem>> {
        return movieRepository.getPagingFavoriteMovies()
    }

    override suspend fun setFavoriteMovie(movie: Movie, state: Boolean) {
        movieRepository.setFavoriteMovie(movie = movie, state = state)
    }

    override suspend fun checkIsFavoriteMovie(movieId: Long): Boolean {
        return movieRepository.checkIsFavorite(movieId = movieId)
    }
}