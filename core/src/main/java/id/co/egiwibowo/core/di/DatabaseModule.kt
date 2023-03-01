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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): GMovieDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("1A05CE4D1B628663F411A8086D99".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            GMovieDatabase::class.java, "Gmovie.db")
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMovieDao(database: GMovieDatabase): MovieDao = database.getMovieDao()
}