package id.co.egiwibowo.gmovie.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import id.co.egiwibowo.core.domain.models.MovieItem
import id.co.egiwibowo.gmovie.R

@Composable
fun DiscoverSection(
    modifier: Modifier = Modifier,
    title: String,
    onClickShowAll: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "See more",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable {
                        onClickShowAll()
                    }
            )
        }
        content()
    }
}

@Composable
fun DiscoverSectionError(onRetry: () -> Unit) {
    Column(
        modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.try_again), modifier = Modifier.clickable {
            onRetry()
        })
    }
}

@Composable
fun HorizontalListMovies(
    modifier: Modifier = Modifier,
    movies: List<MovieItem>,
    onClick: (movie: MovieItem) -> Unit
) {
    LazyRow(modifier = modifier.fillMaxWidth()) {
        itemsIndexed(movies) {index, item ->
            CardMovieSmall(
                modifier = Modifier.padding(start = if (index == 0) 0.dp else 8.dp, end = 8.dp),
                title = item.title,
                imageUrl = item.posterUrl
            ) {
                onClick(item)
            }
        }
    }
}

@Composable
fun HorizontalListMoviesLoading() {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(3) {
            CardMovieSmall(
                modifier = Modifier.padding(start = if (it == 0) 0.dp else 8.dp, end = 8.dp),
                title = "",
                imageUrl = "",
                loading = true,
                enabled = false
            ) {}
        }
    }
}