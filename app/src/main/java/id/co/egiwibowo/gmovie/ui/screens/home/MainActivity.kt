package id.co.egiwibowo.gmovie.ui.screens.home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.hilt.android.AndroidEntryPoint
import id.co.egiwibowo.core.domain.models.TypeMovies
import id.co.egiwibowo.gmovie.R
import id.co.egiwibowo.gmovie.ui.screens.movie.MovieActivity
import id.co.egiwibowo.gmovie.ui.screens.movies.MoviesActivity
import id.co.egiwibowo.gmovie.ui.theme.GMovieTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GMovieTheme {
                HomeScreen(
                    viewModel = viewModel,
                    navToMovie = { goToMovie(it) },
                    navToMovies = { goToMovies(it) },
                    navToFavorite = {
                        val moduleName = "favorite"
                        checkHaveModule(moduleName) {
                            navToFavorite()
                        }
                    }
                )
            }
        }
    }

    private fun goToMovies(typeMovies: TypeMovies) {
        startActivity(MoviesActivity.getIntent(this, typeMovies.name))
    }

    private fun goToMovie(movieId: Long) {
        startActivity(MovieActivity.getIntent(this, movieId))
    }

    private fun navToFavorite() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("app://id.co.egiwibowo.gmovie.favorite")
            `package` = this@MainActivity.packageName
        }
        startActivity(intent)
    }

    private fun checkHaveModule(
        moduleName: String,
        installFailed: () -> Unit = {},
        action: () -> Unit,
    ) {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        if (splitInstallManager.installedModules.contains(moduleName)) {
            action()
        } else {
            val request = requestSplitInstall(moduleName)
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        getString(R.string.install_module_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    action()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        getString(R.string.install_module_failure),
                        Toast.LENGTH_SHORT
                    ).show()
                    installFailed()
                }
        }
    }

    private fun requestSplitInstall(moduleName: String): SplitInstallRequest {
        return SplitInstallRequest.newBuilder()
            .addModule(moduleName)
            .build()
    }
}