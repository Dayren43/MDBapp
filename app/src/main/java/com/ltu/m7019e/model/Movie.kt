package com.ltu.m7019e.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable

data class MovieResponse(
    @SerialName("results") val results: List<Movie> = listOf(),
)

@Entity(tableName = "favorite_movies")
@Serializable
data class Movie(
    @PrimaryKey @SerialName("id") var id: Long = 0L,
    @SerialName("title") var title: String,
    @SerialName("poster_path") var posterPath: String,
    @SerialName("backdrop_path") var backdropPath: String?,
    @SerialName("release_date") var releaseDate: String,
    @SerialName("overview") var overview: String
)
