package id.co.egiwibowo.gmovie.ui.screens.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import id.co.egiwibowo.gmovie.ui.components.CardMovie
import id.co.egiwibowo.gmovie.ui.components.DiscoverSectionError
import id.co.egiwibowo.gmovie.ui.components.itemsAsPagingViewState
import id.co.egiwibowo.gmovie.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel,
    navToMovie: (movieId: Long) -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.title_movies),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.content_desc_icon)
                        )
                    }
                }
            )
        }
    ) {
        MoviesContent(
            viewModel = viewModel,
            paddingValues = it,
            navToMovie = navToMovie
        )
    }
}

@Composable
fun MoviesContent(
    viewModel: MoviesViewModel,
    paddingValues: PaddingValues,
    navToMovie: (movieId: Long) -> Unit
) {

    val pagingMovies = viewModel.pagingDataFlow.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp)
        ) {

            itemsAsPagingViewState(
                lazyPagingItems = pagingMovies,
                refreshLoading = {
                    CardMovie(loading = true)
                },
                pagingLoading = {
                    CardMovie(loading = true)
                },
                refreshError = {
                    DiscoverSectionError {
                        pagingMovies.refresh()
                    }
                },
                pagingError = {
                    DiscoverSectionError {
                        pagingMovies.retry()
                    }
                }

            ) {
                CardMovie(
                    imageUrl = it.posterUrl,
                    title = it.title
                ) {
                    navToMovie(it.id)
                }
            }
        }
    }
}
