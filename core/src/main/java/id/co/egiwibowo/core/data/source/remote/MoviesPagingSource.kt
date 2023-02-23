package id.co.egiwibowo.core.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.co.egiwibowo.core.data.source.remote.network.ApiService
import id.co.egiwibowo.core.data.source.remote.reponse.MovieItemDTOResponse
import id.co.egiwibowo.core.data.source.remote.reponse.MoviesDTOResponse
import id.co.egiwibowo.core.domain.models.TypeMovies

class MoviesPagingSource(
    private val service: ApiService,
    private val typeMovies: TypeMovies
): PagingSource<Int, MovieItemDTOResponse>() {
    override fun getRefreshKey(state: PagingState<Int, MovieItemDTOResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private suspend fun mapTypeToServices(type: TypeMovies, page: Int): MoviesDTOResponse {
        return when(type) {
            TypeMovies.NOW_PLAYING -> service.getNowPlayingMovies(page = page)
            TypeMovies.TOP_RATED -> service.getTopRatedMovies(page = page)
            TypeMovies.POPULAR -> service.getPopularMovies(page = page)
            TypeMovies.UPCOMING -> service.getUpcomingMovies(page = page)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItemDTOResponse> {
        return try {
            val page = params.key ?: 1
            val response = mapTypeToServices(type = typeMovies, page = page)

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}