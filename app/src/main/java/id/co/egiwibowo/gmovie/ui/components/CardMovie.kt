package id.co.egiwibowo.gmovie.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.placeholder

@Composable
fun CardMovie(
    imageUrl: String = "",
    title: String = "",
    loading: Boolean = false,
    onClick: () -> Unit = {}
) {
    Column(modifier = Modifier
        .padding(10.dp)
        .clickable(enabled = !loading) {
            onClick()
        }) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .placeholder(
                    visible = loading,
                    color = Color.LightGray
                ),
            imageUrl = imageUrl
        )
        Text(text = title, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .placeholder(visible = loading, color = Color.LightGray))
    }
}

@Composable
fun CardMovieSmall(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    loading: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Column(modifier = modifier
        .clickable(enabled = enabled) {
            onClick()
        }
    ) {
        AsyncImage(
            modifier = Modifier
                .width(150.dp)
                .height(100.dp)
                .placeholder(
                    visible = loading,
                    color = Color.LightGray
                ),
            imageUrl = imageUrl,
            loading = loading
        )
        Text(text = title,
            modifier = Modifier
            .width(150.dp)
            .padding(top = 8.dp)
            .placeholder(visible = loading, color = Color.LightGray),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}