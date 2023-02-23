package id.co.egiwibowo.gmovie.ui.screens.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.egiwibowo.core.data.Resource
import id.co.egiwibowo.core.data.getData
import id.co.egiwibowo.core.domain.models.Movie
import id.co.egiwibowo.core.domain.models.MovieItem
import id.co.egiwibowo.core.domain.usecases.MovieUseCase
import id.co.egiwibowo.core.utils.asResource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val useCase: MovieUseCase,
): ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(MovieUIState())
    val uiState: StateFlow<MovieUIState> = _uiState

    val accept: (UiAction) -> Unit

    private val actionStateFlow = MutableSharedFlow<UiAction>()

    val favorite = actionStateFlow
        .filterIsInstance<UiAction.Favorite>()
        .distinctUntilChanged()

    init {
        collectFavorite()

        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }
    }

    fun setMovieId(movieId: Long) {
        _uiState.value = _uiState.value.copy(movieId = movieId)
    }

    private fun collectFavorite() {
        viewModelScope.launch {
            favorite.collectLatest {
                useCase.setFavoriteMovie(it.movie, it.nextState)
                _uiState.value = _uiState.value.copy(isFavorite = it.nextState)
            }
        }
    }

    fun observeMovie(id: Long) {
        viewModelScope.launch {
            useCase.getMovie(id)
                .asResource()
                .onStart { Resource.Loading<Movie>() }
                .collect {
                    _uiState.value = _uiState.value.copy(movieState = it)
                }
        }
    }

    fun onClickFavorite() {
        uiState.value.movie?.let {
            accept(UiAction.Favorite(it, !uiState.value.isFavorite))
        }
    }

    fun checkIsFavorite(id: Long) {
        viewModelScope.launch {
            val isFavorite = useCase.checkIsFavoriteMovie(id)
            _uiState.value = _uiState.value.copy(isFavorite = isFavorite)
        }
    }

    fun observeRecommendationMovies(movieId: Long) {
        viewModelScope.launch {
            useCase.getRecommendationMovies(movieId = movieId)
                .asResource()
                .onStart { Resource.Loading<List<MovieItem>>() }
                .collectLatest {
                    _uiState.value = _uiState.value.copy(recommendationState = it)
                }
        }
    }

    fun onRetryRecommendation() {
        observeRecommendationMovies(_uiState.value.movieId)
    }

}

sealed class UiAction {
    data class Favorite(val movie: Movie, val nextState: Boolean) : UiAction()
}

data class MovieUIState(
    val movieState: Resource<Movie> = Resource.Loading(),
    val recommendationState: Resource<List<MovieItem>> = Resource.Loading(),
    val isFavorite: Boolean = false,
    val movieId: Long = 0
) {
    val movie = movieState.getData()
}