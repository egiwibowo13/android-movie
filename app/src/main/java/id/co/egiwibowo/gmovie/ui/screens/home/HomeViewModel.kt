package id.co.egiwibowo.gmovie.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.egiwibowo.core.data.Resource
import id.co.egiwibowo.core.domain.models.MovieItem
import id.co.egiwibowo.core.domain.usecases.MovieUseCase
import id.co.egiwibowo.core.utils.asResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: MovieUseCase
): ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState

    init {
        observeNowPlayingMovies()
        observePopularMovies()
        observeTopRatedMovies()
        observeUpcomingMovies()
    }

    fun observePopularMovies() {
        viewModelScope.launch {
            useCase.getPopularMovies(page = 1)
                .asResource()
                .onStart { Resource.Loading<List<MovieItem>>() }
                .collect {
                _uiState.value = _uiState.value.copy(popularsMovies = it)
            }
        }
    }

    fun observeTopRatedMovies() {
        viewModelScope.launch {
            useCase.getTopRatedMovies(page = 1)
                .asResource()
                .onStart { Resource.Loading<List<MovieItem>>() }
                .collect {
                    _uiState.value = _uiState.value.copy(topRatedMovies = it)
                }
        }
    }

    fun observeNowPlayingMovies() {
        viewModelScope.launch {
            useCase.getNowPlayingMovies(page = 1)
                .asResource()
                .onStart { Resource.Loading<List<MovieItem>>() }
                .collect {
                    _uiState.value = _uiState.value.copy(nowPlayingMovies = it)
                }
        }
    }

    fun observeUpcomingMovies() {
        viewModelScope.launch {
            useCase.getUpcomingMovies(page = 1)
                .asResource()
                .onStart { Resource.Loading<List<MovieItem>>() }
                .collect {
                    _uiState.value = _uiState.value.copy(upcomingMovies = it)
                }
        }
    }

}

data class HomeUIState(
    val nowPlayingMovies: Resource<List<MovieItem>> = Resource.Loading(),
    val popularsMovies: Resource<List<MovieItem>> = Resource.Loading(),
    val topRatedMovies: Resource<List<MovieItem>> = Resource.Loading(),
    val upcomingMovies: Resource<List<MovieItem>> = Resource.Loading(),

)