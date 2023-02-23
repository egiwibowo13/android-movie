package id.co.egiwibowo.gmovie.ui.screens.movie

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.co.egiwibowo.gmovie.ui.theme.GMovieTheme

@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {

    companion object {
        private const val MOVIE_ID = "movie_id"
        fun getIntent(context: Context, movieId: Long): Intent {
            return Intent(context, MovieActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
        }
    }

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri: Uri? = intent.data
        val action = intent.action
        if (action == Intent.ACTION_VIEW && uri != null) {
            val spPath = uri.path?.split("/")
            val movieId: Long? = spPath?.get(spPath.size - 1)?.toLong()
            movieId?.let {
                viewModel.setMovieId(it)
                viewModel.observeMovie(it)
                viewModel.checkIsFavorite(it)
                viewModel.observeRecommendationMovies(it)
            }
        } else {
            val movieId = intent.extras?.getLong(MOVIE_ID)
            movieId?.let {
                viewModel.setMovieId(it)
                viewModel.observeMovie(it)
                viewModel.checkIsFavorite(it)
                viewModel.observeRecommendationMovies(it)
            }
        }

        setContent {
            GMovieTheme {
                MovieScreen(
                    viewModel = viewModel,
                    navToMovie = { goToMovie(it) },
                    goBack = { finish() }
                )
            }
        }
    }

    private fun goToMovie(movieId: Long) {
        startActivity(getIntent(this, movieId))
    }
}