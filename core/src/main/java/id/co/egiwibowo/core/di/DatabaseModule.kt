package id.co.egiwibowo.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.co.egiwibowo.core.data.source.local.room.GMovieDatabase
import id.co.egiwibowo.core.data.source.local.room.MovieDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GMovieDatabase = Room.databaseBuilder(
        context,
        GMovieDatabase::class.java, "GMovie.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideMovieDao(database: GMovieDatabase): MovieDao = database.getMovieDao()
}