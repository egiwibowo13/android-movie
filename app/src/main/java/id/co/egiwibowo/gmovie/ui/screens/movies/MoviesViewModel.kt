package id.co.egiwibowo.gmovie.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.egiwibowo.core.domain.models.MovieItem
import id.co.egiwibowo.core.domain.models.TypeMovies
import id.co.egiwibowo.core.domain.usecases.MovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val useCase: MovieUseCase,
): ViewModel() {

    private val typeMovies = MutableStateFlow(TypeMovies.POPULAR)

    val pagingDataFlow: Flow<PagingData<MovieItem>>

    init {

        pagingDataFlow = typeMovies.flatMapLatest {
            observePagingMovies(it)
                .cachedIn(viewModelScope)
        }
    }

    fun setTypeMovies(typesMoviesName: String) {
        typeMovies.value = TypeMovies.valueOf(typesMoviesName)
    }

    private fun observePagingMovies(typesMovies: TypeMovies): Flow<PagingData<MovieItem>>  {
        return useCase.getPagingMovies(typesMovies)
    }

}