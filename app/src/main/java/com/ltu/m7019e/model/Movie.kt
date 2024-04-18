package com.ltu.m7019e.model

data class Movie(
    var id: Long = 0L,
    var title: String,
    var posterPath: String,
    var backdropPath: String,
    var releaseDate: String,
    var overview: String
)
