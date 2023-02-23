package id.co.egiwibowo.gmovie.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.placeholder.placeholder

@Composable
fun AsyncImage(
    modifier: Modifier = Modifier.width(100.dp).height(100.dp),
    imageUrl: String,
    loading: Boolean = false,
) {
    SubcomposeAsyncImage(
        modifier = modifier
            .placeholder(
                visible = loading,
                color = Color.LightGray
            ),
        model = imageUrl,
        loading = {
            ImageBoxLoading(
                modifier = modifier
            )
        },
        error = {
            ImageBoxLoading(
                modifier = modifier
            )
        },
        contentScale = ContentScale.FillWidth,
        alignment = Alignment.TopCenter,
        contentDescription = ""
    )
}

@Composable
fun ImageBoxLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .width(100.dp)
        .height(100.dp)
        .placeholder(visible = true, color = Color.LightGray)
    )
}