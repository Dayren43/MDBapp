package com.ltu.m7019e.ui.screens

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.widget.ScrollView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ltu.m7019e.model.Movie
import com.ltu.m7019e.utils.Constants
import com.ltu.m7019e.viewmodel.SelectedMovieUiState

@Composable
fun MovieDetailScreen(
    movie: SelectedMovieUiState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    when (movie) {
        is SelectedMovieUiState.Success -> {
            val movie = movie.movie

            if (isLandscape) {
                Row(modifier = modifier.fillMaxSize()) {
                    // Image on the left
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        AsyncImage(
                            model = Constants.BACKDROP_IMAGE_BASE_URL + Constants.BACKDROP_IMAGE_WIDTH + movie.backdropPath,
                            contentDescription = movie.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Movie details on the right
                    Column(
                        modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = movie.releaseDate,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = movie.overview,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Button(
                            onClick = {
                                val url = Constants.BACKDROP_IMAGE_BASE_URL + "original" + movie.posterPath
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.padding(vertical = 16.dp)
                        ) {
                            Text("Open full quality poster in Browser")
                        }
                    }
                }
            }
            else {
            // Portrait mode
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = Constants.BACKDROP_IMAGE_BASE_URL + Constants.BACKDROP_IMAGE_WIDTH + movie.backdropPath,
                            contentDescription = movie.title,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = movie.releaseDate,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Button(
                        onClick = {
                            val url = Constants.BACKDROP_IMAGE_BASE_URL + "original" + movie.posterPath
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        Text("Open full quality poster in Browser")
                    }
                }
            }
        }
        SelectedMovieUiState.Loading -> {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
        SelectedMovieUiState.Error -> {
            Text(
                text = ":/",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


