package id.co.egiwibowo.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.co.egiwibowo.core.data.MovieRepository
import id.co.egiwibowo.core.domain.repositories.IMovieRepository

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(repository: MovieRepository): IMovieRepository
}