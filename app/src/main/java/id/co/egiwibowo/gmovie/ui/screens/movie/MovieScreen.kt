package id.co.egiwibowo.gmovie.ui.screens.movie

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import id.co.egiwibowo.gmovie.R
import id.co.egiwibowo.gmovie.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    viewModel: MovieViewModel,
    navToMovie: (movieId: Long) -> Unit,
    goBack: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    stringResource(id = R.string.title_movie),
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
    }) {
        MovieContent(
            viewModel = viewModel,
            paddingValues = it,
            navToMovie = navToMovie
        )
    }
}

@Composable
fun MovieContent(
    viewModel: MovieViewModel,
    paddingValues: PaddingValues,
    navToMovie: (movieId: Long) -> Unit
) {

    val uiState = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                imageUrl = uiState.value.movie?.backdropUrl ?: ""
            )
            SectionTitleMovie(
                modifier = Modifier.offset(x = 0.dp, y = 150.dp),
                modifierContainerTitle = Modifier.offset(x = 0.dp, y = 100.dp),
                imageUrl = uiState.value.movie?.posterUrl ?: "",
                title = uiState.value.movie?.title ?: "",
                releaseDate = uiState.value.movie?.releaseDate ?: "",
                genres = uiState.value.movie?.genres?.joinToString(", ") {
                    it.name
                } ?: ""
            )
        }
        SectionOverview(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            overview = uiState.value.movie?.overview ?: ""
        )
        ResourceViewState(
            state = uiState.value.recommendationState,
            loading = { HorizontalListMoviesLoading() },
            error = { DiscoverSectionError {
                viewModel.onRetryRecommendation()
            }}
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(text = stringResource(id = R.string.recommendation))
                HorizontalListMovies(
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                    movies = it
                ) {movieItem ->
                    navToMovie(movieItem.id)
                }
            }
        }

        Button(onClick = {
            viewModel.onClickFavorite()
        }) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}
