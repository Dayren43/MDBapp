package com.ltu.m7019e.viewmodel

import androidx.lifecycle.ViewModel
import com.ltu.m7019e.database.MovieDBUiState
import com.ltu.m7019e.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MovieDBViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(MovieDBUiState())
    val uiState: StateFlow<MovieDBUiState> = _uiState.asStateFlow()

    fun setSelectedMovie(movie: Movie) {
        _uiState.update { currentState ->
            currentState.copy(selectedMovie = movie)
        }
    }
}