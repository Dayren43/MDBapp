package com.ltu.m7019e.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ltu.m7019e.model.PopularCache
import com.ltu.m7019e.model.TopRatedCache

@Dao
interface MovieCacheDaoTopRated {
    @Query("SELECT * FROM topRated_cache")
    suspend fun getCachedMovieList(): List<TopRatedCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheMovieList(movies: List<TopRatedCache>)

    @Query("DELETE FROM topRated_cache")
    suspend fun clearCachedMovieList()
}

@Dao
interface MovieCacheDaoPopular {
    @Query("SELECT * FROM popular_cache")
    suspend fun getCachedMovieList(): List<PopularCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cacheMovieList(movies: List<PopularCache>)
    @Query("DELETE FROM popular_cache")
    suspend fun clearCachedMovieList()
}

