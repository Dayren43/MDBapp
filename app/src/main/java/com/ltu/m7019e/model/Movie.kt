package com.ltu.m7019e.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
@Serializable

data class MovieResponse(
    @SerialName("results") val results: List<Movie> = listOf(),
)

@Serializable
data class Movie(
    @SerialName("id") var id: Long = 0L,
    @SerialName("title") var title: String,
    @SerialName("poster_path") var posterPath: String,
    @SerialName("backdrop_path") var backdropPath: String?,
    @SerialName("release_date") var releaseDate: String,
    @SerialName("overview") var overview: String
)

@Entity(tableName = "favorite_movies")
@Serializable
data class FavoriteMovies(
    @PrimaryKey @SerialName("id") var id: Long = 0L,
    val movie: Movie
)

@Entity(tableName = "topRated_cache")
@Serializable
data class TopRatedCache(
    @PrimaryKey @SerialName("id") var id: Long = 0L,
    val movie: Movie
)

@Entity(tableName = "popular_cache")
@Serializable
data class PopularCache(
    @PrimaryKey @SerialName("id") var id: Long = 0L,
    val movie: Movie
)
object MovieTypeConverter {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    @JvmStatic
    fun fromMovie(movie: Movie): String {
        return json.encodeToString(movie)
    }

    @TypeConverter
    @JvmStatic
    fun toMovie(jsonString: String): Movie {
        return json.decodeFromString(jsonString)
    }
}