package com.ltu.m7019e.Network

import com.ltu.m7019e.model.Movie
import com.ltu.m7019e.model.MovieResponse
import com.ltu.m7019e.model.PersonResponse
import com.ltu.m7019e.utils.SECRETS
import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDBApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey: String = SECRETS.API_KEY
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String = SECRETS.API_KEY
    ): MovieResponse


    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query("api_key")
        apiKey: String = SECRETS.API_KEY
    ): Movie

    @GET("people/popular")
    suspend fun getPopularPeople(
        @Query("api_key")
        apiKey: String = SECRETS.API_KEY
    ): PersonResponse


}