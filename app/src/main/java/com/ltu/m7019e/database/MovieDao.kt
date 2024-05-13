package com.ltu.m7019e.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ltu.m7019e.model.FavoriteMovies

@Dao
interface MovieDao {
    @Query("SELECT * FROM favorite_movies")
    suspend fun getFavoriteMovies(): List<FavoriteMovies>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteMovie(movie: FavoriteMovies)
    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    suspend fun getMovie(id: Long): FavoriteMovies

    @Query("DELETE FROM favorite_movies WHERE id = :id")
    suspend fun deleteFavoriteMovie(id: Long)


}
