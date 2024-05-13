package com.ltu.m7019e.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ltu.m7019e.model.FavoriteMovies
import com.ltu.m7019e.model.MovieTypeConverter
import com.ltu.m7019e.model.PopularCache
import com.ltu.m7019e.model.TopRatedCache

@Database(entities = [FavoriteMovies::class, TopRatedCache::class, PopularCache::class], version = 3, exportSchema = false)
@TypeConverters(MovieTypeConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun movieCacheDaoPopular(): MovieCacheDaoPopular
    abstract fun movieCacheDaoTopRated(): MovieCacheDaoTopRated

    companion object{
        @Volatile
        private var Instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase{
            return Instance?: synchronized(this){
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}