package com.ltu.m7019e.model

data class Person(
    var adult: Boolean,
    var gender: Int,
    var id: Int,
    var known_for_department: String,
    var name: String,
    var original_name: String,
    var popularity: Float,
    var profile_path: String,
    var known_for: List<Int>
)
