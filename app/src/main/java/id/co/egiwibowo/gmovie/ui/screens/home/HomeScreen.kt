package id.co.egiwibowo.gmovie.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import id.co.egiwibowo.core.domain.models.TypeMovies
import id.co.egiwibowo.gmovie.R
import id.co.egiwibowo.gmovie.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navToMovie: (movieId: Long) -> Unit,
    navToMovies: (typeMovies: TypeMovies) -> Unit,
    navToFavorite: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = navToFavorite) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = stringResource(id = R.string.content_desc_icon)
                        )
                    }
                }
            )
        }
    ) {
        HomeContent(
            viewModel = viewModel,
            paddingValues = it,
            navToMovie = navToMovie,
            navToMovies = navToMovies
        )
    }
}


@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    paddingValues: PaddingValues,
    navToMovie: (movieId: Long) -> Unit,
    navToMovies: (typeMovies: TypeMovies) -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.padding(paddingValues),

    ) {
        item {

            DiscoverSection(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                title = stringResource(id = R.string.sub_title_now_playing),
                onClickShowAll = {
                    navToMovies(TypeMovies.NOW_PLAYING)
                }
            ) {
                ResourceViewState(
                    state = uiState.nowPlayingMovies,
                    loading = { HorizontalListMoviesLoading() },
                    error = {
                        DiscoverSectionError {
                            viewModel.observeNowPlayingMovies()
                        }
                    }
                ) { movies ->
                    HorizontalListMovies(movies = movies) { movie ->
                        navToMovie(movie.id)
                    }
                }
            }
        }
        item {

            DiscoverSection(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                title = stringResource(id = R.string.sub_title_popular),
                onClickShowAll = {
                    navToMovies(TypeMovies.POPULAR)
                }
            ) {
                ResourceViewState(
                    state = uiState.popularsMovies,
                    loading = { HorizontalListMoviesLoading() },
                    error = {
                        DiscoverSectionError {
                            viewModel.observePopularMovies()
                        }
                    }
                ) { movies ->
                    HorizontalListMovies(movies = movies) { movie ->
                        navToMovie(movie.id)
                    }
                }
            }
        }
        item {
            DiscoverSection(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                title = stringResource(id = R.string.sub_title_top_rated),
                onClickShowAll = {
                    navToMovies(TypeMovies.TOP_RATED)
                }
            ) {
                ResourceViewState(
                    state = uiState.topRatedMovies,
                    loading = { HorizontalListMoviesLoading() },
                    error = {
                        DiscoverSectionError {
                            viewModel.observeTopRatedMovies()
                        }
                    }
                ) { movies ->
                    HorizontalListMovies(movies = movies) { movie ->
                        navToMovie(movie.id)
                    }
                }
            }
        }
        item {
            DiscoverSection(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                title = stringResource(id = R.string.sub_title_upcoming),
                onClickShowAll = {
                    navToMovies(TypeMovies.UPCOMING)
                }
            ) {
                ResourceViewState(
                    state = uiState.upcomingMovies,
                    loading = { HorizontalListMoviesLoading() },
                    error = {
                        DiscoverSectionError {
                            viewModel.observeUpcomingMovies()
                        }
                    }
                ) { movies ->
                    HorizontalListMovies(movies = movies) {movie ->
                        navToMovie(movie.id)
                    }
                }
            }
        }
        }
}
