package id.co.egiwibowo.gmovie.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.placeholder
import id.co.egiwibowo.gmovie.R

@Composable
fun SectionTitleMovie(
    modifier: Modifier = Modifier,
    modifierContainerTitle: Modifier = Modifier,
    loading: Boolean = false,
    imageUrl: String,
    title: String,
    releaseDate: String,
    genres: String
) {
    Row(modifier = modifier.padding(horizontal = 16.dp)) {
        AsyncImage(
            modifier = Modifier
                .width(150.dp)
                .height(200.dp)
                .placeholder(
                    visible = loading,
                    color = Color.LightGray
                ),
            imageUrl = imageUrl,
            loading = loading
        )
        Column(modifier = modifierContainerTitle.padding(start = 8.dp, end = 16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = stringResource(R.string.release_date, releaseDate))
            Text(text = genres)
        }
    }
}