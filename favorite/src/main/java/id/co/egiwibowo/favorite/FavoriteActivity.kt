package id.co.egiwibowo.favorite

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.EntryPointAccessors
import id.co.egiwibowo.gmovie.di.FavoriteModuleDependencies
import id.co.egiwibowo.gmovie.ui.theme.GMovieTheme
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)

        setContent {
            GMovieTheme {
                FavoriteScreen(
                    viewModel = viewModel,
                    goBack = { goBack() },
                    navToMovie = {
                        navToMovie(it)
                    }
                )
            }
        }
    }

    private fun goBack() {
        finish()
    }

    private fun navToMovie(movieId: Long) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("app://id.co.egiwibowo.gmovie/movie/${movieId}")
            `package` = applicationContext.packageName
        }
        startActivity(intent)
    }
}