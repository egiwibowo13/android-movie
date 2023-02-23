package id.co.egiwibowo.gmovie.ui.screens.movies

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.co.egiwibowo.gmovie.ui.screens.movie.MovieActivity
import id.co.egiwibowo.gmovie.ui.theme.GMovieTheme

@AndroidEntryPoint
class MoviesActivity : AppCompatActivity() {

    companion object {
        private const val MOVIE_TYPES = "movie_types"
        fun getIntent(context: Context, movieTypes: String): Intent {
            return Intent(context, MoviesActivity::class.java).apply {
                putExtra(MOVIE_TYPES, movieTypes)
            }
        }
    }

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val typeMoviesName = intent.extras?.getString(MOVIE_TYPES)
        typeMoviesName?.let {
            viewModel.setTypeMovies(it)
        }

        setContent {
            GMovieTheme {
                MoviesScreen(
                    viewModel = viewModel,
                    navToMovie = { goToMovie(it) },
                    goBack = { finish() }
                )
            }
        }
    }

    private fun goToMovie(movieId: Long) {
        startActivity(MovieActivity.getIntent(this, movieId))
    }
}