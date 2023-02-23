package id.co.egiwibowo.gmovie.ui.components

import androidx.compose.runtime.Composable
import id.co.egiwibowo.core.data.Resource

@Composable
fun <T> ResourceViewState(
    state: Resource<T>,
    loading: @Composable () -> Unit,
    error: @Composable (throwable: Throwable?) -> Unit,
    view: @Composable (data: T) -> Unit
) {
    when(state) {
        is Resource.Success -> view(state.data)
        is Resource.Loading -> loading()
        is Resource.Error -> error(state.throwable)
    }
}