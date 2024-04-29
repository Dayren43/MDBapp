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


