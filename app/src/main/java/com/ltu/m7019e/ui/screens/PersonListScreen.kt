package com.ltu.m7019e.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ltu.m7019e.model.Movie
import com.ltu.m7019e.model.Person
import com.ltu.m7019e.ui.theme.TheMovideDBV24Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ltu.m7019e.utils.Constants


@Composable
fun PersonListScreen(
    personList: List<Person>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(personList) { person ->
            PersonListScreenCard(
                person = person,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonListScreenCard(
    person: Person,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = {}
    ) {

        Row() {
            Box(
                modifier = Modifier
            ) {
                AsyncImage(
                    model = Constants.POSTER_IMAGE_BASE_URL + Constants.POSTER_IMAGE_WIDTH + person.profile_path,
                    contentDescription = person.name,
                    modifier = modifier
                        .width(92.dp)
                        .height(138.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = person.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Sex: ${mapGender(person.gender)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Known For ${person.known_for_department}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Popularity: ${person.popularity}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

private fun mapGender(gender: Int): String {
    return when (gender) {
        1 -> "Female"
        2 -> "Male"
        else -> "Other"
    }
}

@Preview(showBackground = true)
@Composable
fun PersonListScreenPreview() {
    TheMovideDBV24Theme {
        PersonListScreenCard(
            person = Person(
                adult = false,
                gender = 2,
                id = 976,
                known_for_department = "Acting",
                name = "Jason Statham",
                original_name = "Jason Statham",
                popularity = 219.552f,
                profile_path = "/whNwkEQYWLFJA8ij0WyOOAD5xhQ.jpg",
                known_for = listOf(345940, 4108, 337339) // List of movie IDs
            )
        )
    }
}