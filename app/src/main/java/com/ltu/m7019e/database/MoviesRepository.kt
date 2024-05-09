package com.ltu.m7019e.database

import com.ltu.m7019e.Network.MovieDBApiService
import com.ltu.m7019e.model.Movie
import com.ltu.m7019e.model.MovieResponse


interface MoviesRepository {
    suspend fun getPopularMovies(): MovieResponse
    suspend fun getTopRatedMovies(): MovieResponse
    suspend fun getMoviesByIds(movieIds: List<Int>): MovieResponse
}

class NetworkMoviesRepository(private val apiService: MovieDBApiService) : MoviesRepository {
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
}

interface SavedMovieRepository {
    suspend fun getSavedMovies(): List<Movie>
    suspend fun insertMovie(movie: Movie)
    suspend fun getMovie(id:Long): Movie
    suspend fun deleteMovie(movie: Movie)
}

class FavoriteMoviesRepository(private val movieDao: MovieDao) : SavedMovieRepository {
    override suspend fun getSavedMovies(): List<Movie> {
        return movieDao.getFavoriteMovies()
    }

    override suspend fun insertMovie(movie: Movie) {
        movieDao.insertFavoriteMovie(movie)
    }

    override suspend fun getMovie(id: Long): Movie {
        return movieDao.getMovie(id)
    }

    override suspend fun deleteMovie(movie: Movie) {
        movieDao.deleteFavoriteMovie(movie.id)
    }
}
