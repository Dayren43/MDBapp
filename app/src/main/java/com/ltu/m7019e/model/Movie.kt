package com.ltu.m7019e.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
