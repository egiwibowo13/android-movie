package id.co.egiwibowo.core.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.co.egiwibowo.core.data.source.remote.reponse.MovieItemDTOResponse
import id.co.egiwibowo.core.domain.models.TypeMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class MoviesPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val typeMovies: TypeMovies
): PagingSource<Int, MovieItemDTOResponse>() {
    override fun getRefreshKey(state: PagingState<Int, MovieItemDTOResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private fun mapTypeToServices(type: TypeMovies, page: Int): Flow<List<MovieItemDTOResponse>> {
        return when(type) {
            TypeMovies.NOW_PLAYING -> remoteDataSource.getNowPlayingMovies(page = page)
            TypeMovies.TOP_RATED -> remoteDataSource.getTopRatedMovies(page = page)
            TypeMovies.POPULAR -> remoteDataSource.getPopularMovies(page = page)
            TypeMovies.UPCOMING -> remoteDataSource.getUpcomingMovies(page = page)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItemDTOResponse> {
        return try {
            val page = params.key ?: 1
            val response = mapTypeToServices(type = typeMovies, page = page).first()


            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}