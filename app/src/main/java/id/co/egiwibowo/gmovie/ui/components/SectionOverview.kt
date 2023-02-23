package id.co.egiwibowo.gmovie.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import id.co.egiwibowo.gmovie.R

@Composable
fun SectionOverview(
    modifier: Modifier = Modifier,
    overview: String
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.overview),
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = overview, modifier = Modifier.padding(top = 8.dp))
    }
}