package id.co.egiwibowo.favorite

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import id.co.egiwibowo.gmovie.di.FavoriteModuleDependencies

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }

}