package com.ltu.m7019e.database

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ltu.m7019e.Network.MovieDBApiService
import com.ltu.m7019e.model.FavoriteMovies
import com.ltu.m7019e.model.Movie
import com.ltu.m7019e.model.MovieResponse
import com.ltu.m7019e.model.PopularCache
import com.ltu.m7019e.model.TopRatedCache
import com.ltu.m7019e.workers.RefreshMovieCacheWorker


interface MoviesRepository {
    suspend fun getPopularMovies(): MovieResponse
    suspend fun getTopRatedMovies(): MovieResponse
    suspend fun getMoviesByIds(movieIds: List<Int>): MovieResponse
    suspend fun popularNetworkRefresh()
    suspend fun topRatedNetworkRefresh()
    suspend fun cancelRefresh()
}

class NetworkMoviesRepository(private val context: Context, private val apiService: MovieDBApiService) : MoviesRepository {
    private val workManager = WorkManager.getInstance(context)
    override suspend fun getPopularMovies(): MovieResponse {
        return apiService.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): MovieResponse {
        return apiService.getTopRatedMovies()
    }

    override suspend fun getMoviesByIds(movieIds: List<Int>): MovieResponse {
        val movies = mutableListOf<Movie>()
        movieIds.forEach { movieId ->
            val movie = apiService.getMovieById(movieId = movieId)
            movies.add(movie)
        }
        return MovieResponse(results = movies)
    }

    override suspend fun popularNetworkRefresh() {
        val req = OneTimeWorkRequestBuilder<RefreshMovieCacheWorker>()
            .setInputData(Data.Builder().putString("type", "popular").build())
            .setConstraints(Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build())
            .build()
        workManager.enqueueUniqueWork("Refresh", ExistingWorkPolicy.REPLACE, req)
    }

    override suspend fun topRatedNetworkRefresh() {
        val req = OneTimeWorkRequestBuilder<RefreshMovieCacheWorker>()
            .setInputData(Data.Builder().putString("type", "top_rated").build())
            .setConstraints(Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build())
            .build()
        workManager.enqueueUniqueWork("Refresh", ExistingWorkPolicy.REPLACE, req)
    }

    override suspend fun cancelRefresh() {
        workManager.cancelUniqueWork("Refresh")
    }
}

interface SavedMovieRepository {
    suspend fun getSavedMovies(): List<Movie>
    suspend fun insertMovie(movie: Movie)
    suspend fun getMovie(id:Long): Movie
    suspend fun deleteMovie(movie: Movie)
}

class FavoriteMoviesRepository(private val movieDao: MovieDao) : SavedMovieRepository {
    override suspend fun getSavedMovies(): List<Movie> {
        return movieDao.getFavoriteMovies().map { it.movie }
    }

    override suspend fun insertMovie(movie: Movie) {
        movieDao.insertFavoriteMovie(FavoriteMovies(id = movie.id, movie = movie))
    }

    override suspend fun getMovie(id: Long): Movie {
        return movieDao.getMovie(id).movie
    }

    override suspend fun deleteMovie(movie: Movie) {
        movieDao.deleteFavoriteMovie(movie.id)
    }
}

interface CashedMovieRepository {
    suspend fun getCachedMovieList(): List<Movie>
    suspend fun cacheMovieList(movies: List<Movie>)
    suspend fun clearCachedMovieList()
}

class PopularCacheMoviesRepository(private val movieCacheDao: MovieCacheDaoPopular) : CashedMovieRepository {
    override suspend fun getCachedMovieList(): List<Movie> {
        return movieCacheDao.getCachedMovieList().map { it.movie }
    }
    override suspend fun cacheMovieList(movies: List<Movie>) {
        var count = 0L;
        movieCacheDao.cacheMovieList(movies.map {
            count++
            PopularCache(id = count, movie = it)
        })
    }
    override suspend fun clearCachedMovieList(){
        movieCacheDao.clearCachedMovieList()
    }
}

class TopRatedCacheMoviesRepository(private val movieCacheDao: MovieCacheDaoTopRated) : CashedMovieRepository {
    override suspend fun getCachedMovieList(): List<Movie> {
        return movieCacheDao.getCachedMovieList().map { it.movie }
    }
    override suspend fun cacheMovieList(movies: List<Movie>) {
        var count = 0L;
        movieCacheDao.cacheMovieList(movies.map {
            count++
            TopRatedCache(id = count, movie = it)
        })
    }
    override suspend fun clearCachedMovieList(){
        movieCacheDao.clearCachedMovieList()
    }
}