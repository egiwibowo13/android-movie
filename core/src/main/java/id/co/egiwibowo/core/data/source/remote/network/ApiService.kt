package id.co.egiwibowo.core.data.source.remote.network

import id.co.egiwibowo.core.config.Config
import id.co.egiwibowo.core.data.source.remote.reponse.MovieDTOResponse
import id.co.egiwibowo.core.data.source.remote.reponse.MoviesDTOResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing?api_key=${Config.API_KEY}")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
    ): MoviesDTOResponse

    @GET("movie/upcoming?api_key=${Config.API_KEY}")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
    ): MoviesDTOResponse

    @GET("movie/popular?api_key=${Config.API_KEY}")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): MoviesDTOResponse

    @GET("movie/top_rated?api_key=${Config.API_KEY}")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): MoviesDTOResponse


    @GET("movie/{movieId}?api_key=${Config.API_KEY}")
    suspend fun getMovie(
        @Path("movieId") movieId: Long
    ): MovieDTOResponse

    @GET("movie/{movieId}/recommendations?api_key=${Config.API_KEY}")
    suspend fun getRecommendationMovies(
        @Path("movieId") movieId: Long
    ): MoviesDTOResponse
}