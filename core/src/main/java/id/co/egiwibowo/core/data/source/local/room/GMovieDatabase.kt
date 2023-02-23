package id.co.egiwibowo.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.co.egiwibowo.core.data.source.local.entities.GenreDataConverter
import id.co.egiwibowo.core.data.source.local.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 4, exportSchema = false)
@TypeConverters(GenreDataConverter::class)
abstract class GMovieDatabase: RoomDatabase()  {
    abstract fun getMovieDao(): MovieDao
}