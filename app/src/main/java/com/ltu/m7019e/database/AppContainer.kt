package com.ltu.m7019e.database


import android.content.Context
import com.ltu.m7019e.Network.MovieDBApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ltu.m7019e.utils.Constants

interface AppContainer {
    val moviesRepository: MoviesRepository
    val savedMovieRepository: SavedMovieRepository
    val popularCacheMoviesRepository: PopularCacheMoviesRepository
    val topRatedCacheMoviesRepository: TopRatedCacheMoviesRepository
}

class DefaultAppContainer(context: Context) : AppContainer{

    val movieDBJson = Json{
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(
            okhttp3.OkHttpClient.Builder()
                .connectTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(movieDBJson.asConverterFactory("application/json".toMediaType()))
        .baseUrl(Constants.MOVIE_LIST_BASE_URL)
        .build()


    private val retrofitService: MovieDBApiService by lazy {
        retrofit.create(MovieDBApiService::class.java)
    }

    override val moviesRepository: MoviesRepository by lazy {
        NetworkMoviesRepository(context, retrofitService)
    }

    override val savedMovieRepository: SavedMovieRepository by lazy {
        FavoriteMoviesRepository(MovieDatabase.getDatabase(context).movieDao())
    }

    override val popularCacheMoviesRepository: PopularCacheMoviesRepository by lazy {
        PopularCacheMoviesRepository(MovieDatabase.getDatabase(context).movieCacheDaoPopular())
    }

    override val topRatedCacheMoviesRepository: TopRatedCacheMoviesRepository by lazy {
        TopRatedCacheMoviesRepository(MovieDatabase.getDatabase(context).movieCacheDaoTopRated())
    }

}