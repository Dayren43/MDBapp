package com.ltu.m7019e.database

import com.ltu.m7019e.model.Person

class People {
    fun getPeople(): List<Person> {
        return listOf<Person>(
            Person(
                adult = false,
                gender = 1,
                id = 989325,
                known_for_department = "Acting",
                name = "Ella Purnell",
                original_name = "Ella Purnell",
                popularity = 380.104f,
                profile_path = "/xIZojkBo3Cf0GnchjC8hSS5P105.jpg",
                known_for = listOf(503736, 94605, 283366) // List of movie IDs and TV show IDs
            ),
            Person(
                adult = false,
                gender = 2,
                id = 976,
                known_for_department = "Acting",
                name = "Jason Statham",
                original_name = "Jason Statham",
                popularity = 219.552f,
                profile_path = "/whNwkEQYWLFJA8ij0WyOOAD5xhQ.jpg",
                known_for = listOf(345940, 4108, 337339) // List of movie IDs
            ),
            Person(
                adult = false,
                gender = 1,
                id = 1813,
                known_for_department = "Acting",
                name = "Anne Hathaway",
                original_name = "Anne Hathaway",
                popularity = 173.611f,
                profile_path = "/qyigJ4qyrxZdVwz89uZwQaTvXX8.jpg",
                known_for = listOf(157336, 350, 49026) // List of movie IDs
            ),
            Person(
                adult = false,
                gender = 1,
                id = 3878062,
                known_for_department = "Acting",
                name = "Supaporn Malisorn",
                original_name = "Supaporn Malisorn",
                popularity = 166.035f,
                profile_path = "/2e2uXZc3dubNIFL0oHsRFwoNiL5.jpg",
                known_for = listOf(248276, 752896, 93058) // List of movie IDs and TV show IDs
            ),
            Person(
                adult = false,
                gender = 1,
                id = 54693,
                known_for_department = "Acting",
                name = "Emma Stone",
                original_name = "Emma Stone",
                popularity = 101.196f,
                profile_path = "/cZ8a3QvAnj2cgcgVL6g4XaqPzpL.jpg",
                known_for = listOf(337404, 1930, 313369) // List of movie IDs
            ),
        )
    }
}