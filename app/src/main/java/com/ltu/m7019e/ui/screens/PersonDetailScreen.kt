package com.ltu.m7019e.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import com.ltu.m7019e.viewmodel.SelectedPersonUiState


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ltu.m7019e.utils.Constants

@Composable
fun PersonDetailScreen(
    persone: SelectedPersonUiState,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    when (persone) {
        is SelectedPersonUiState.Success -> {
            val person = persone.person
            Column(modifier = modifier) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .width(92.dp)
                            .height(138.dp)
                    ) {
                        AsyncImage(
                            model = Constants.POSTER_IMAGE_BASE_URL + Constants.BACKDROP_IMAGE_WIDTH + person.profile_path,
                            contentDescription = person.name,
                            modifier = Modifier.fillMaxSize(),
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
                Spacer(modifier = Modifier.size(8.dp))
                LazyRow(modifier = Modifier) {
                    items(persone.movies) { movie ->
                        MovieListItemCard(
                            movie = movie,
                            {},
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
        SelectedPersonUiState.Loading -> {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
        SelectedPersonUiState.Error -> {
            Text(
                text = ":/",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

