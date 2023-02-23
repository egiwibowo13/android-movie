package id.co.egiwibowo.gmovie.ui.components

import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

fun <T: Any> LazyGridScope.itemsAsPagingViewState(
    lazyPagingItems: LazyPagingItems<T>,
    refreshLoading: @Composable LazyGridItemScope.() -> Unit = {},
    refreshError: @Composable LazyGridItemScope.() -> Unit = {},
    pagingLoading: @Composable LazyGridItemScope.() -> Unit = {},
    pagingError: @Composable LazyGridItemScope.() -> Unit = {},
    itemContent: @Composable LazyGridItemScope.(item: T) -> Unit,
) {
    items(lazyPagingItems.itemCount) { index ->
        lazyPagingItems[index]?.let { itemContent(it) }
    }
    when(lazyPagingItems.loadState.refresh) {
        is LoadState.Error -> {
            item { refreshError() }
        }
        is LoadState.Loading -> { // Loading UI
            items(count = 6) {
                refreshLoading()
            }
        }
        else -> {}
    }

    when (lazyPagingItems.loadState.append) { // Pagination
        is LoadState.Error -> {
            item {
                pagingError()
            }
        }
        is LoadState.Loading -> { // Pagination Loading UI
            items(2) {
                pagingLoading()
            }
        }
        else -> {}
    }
}