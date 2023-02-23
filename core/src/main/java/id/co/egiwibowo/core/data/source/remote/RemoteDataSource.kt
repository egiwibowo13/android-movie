package id.co.egiwibowo.core.data.source.remote

import android.util.Log
import id.co.egiwibowo.core.data.source.remote.network.ApiService
import id.co.egiwibowo.core.data.source.remote.reponse.MovieDTOResponse
import id.co.egiwibowo.core.data.source.remote.reponse.MovieItemDTOResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun getNowPlayingMovies(page: Int): Flow<List<MovieItemDTOResponse>> {
        return flow {
            try {
                val response = apiService.getNowPlayingMovies(page = page)
                emit(response.results)
            } catch (e : Exception) {
                throw e
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getUpcomingMovies(page: Int): Flow<List<MovieItemDTOResponse>> {
        return flow {
            try {
                val response = apiService.getUpcomingMovies(page = page)
                emit(response.results)
            } catch (e : Exception) {
                throw e
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getPopularMovies(page: Int): Flow<List<MovieItemDTOResponse>> {
        return flow {
            try {
                val response = apiService.getPopularMovies(page = page)
                emit(response.results)
            } catch (e : Exception) {
                throw e
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getTopRatedMovies(page: Int): Flow<List<MovieItemDTOResponse>> {
        return flow {
            try {
                val response = apiService.getTopRatedMovies(page = page)
                emit(response.results)
            } catch (e : Exception) {
                throw e
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getRecommendation(movieId: Long): Flow<List<MovieItemDTOResponse>> {
        return flow {
            try {
                Log.d("getRecommendation", movieId.toString())
                val response = apiService.getRecommendationMovies(movieId = movieId)
                Log.d("getRecommendation", response.toString())
                emit(response.results)
            } catch (e : Exception) {
                Log.d("getRecommendation", e.toString())
                throw e
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMovie(movieId: Long): Flow<MovieDTOResponse> {
       return flow {
           try {
               val response =  apiService.getMovie(movieId = movieId)
               emit(response)
           } catch (e: Exception) {
               throw e
           }
       }.flowOn(Dispatchers.IO)
    }
}