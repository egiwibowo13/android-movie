package id.co.egiwibowo.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.co.egiwibowo.core.domain.models.MovieItem
import id.co.egiwibowo.core.domain.usecases.MovieUseCase
import kotlinx.coroutines.flow.Flow

class FavoriteViewModel constructor(
    useCase: MovieUseCase,
): ViewModel() {

    val pagingDataFlow: Flow<PagingData<MovieItem>> = useCase.getPagingFavoriteMovies()
        .cachedIn(viewModelScope)

}