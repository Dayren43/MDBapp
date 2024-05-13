package com.ltu.m7019e.viewmodel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ltu.m7019e.MovieDBApplication
import com.ltu.m7019e.database.MoviesRepository
import com.ltu.m7019e.database.PopularCacheMoviesRepository
import com.ltu.m7019e.database.SavedMovieRepository
import com.ltu.m7019e.database.TopRatedCacheMoviesRepository
import com.ltu.m7019e.model.Movie
import com.ltu.m7019e.model.Person
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface MovieListUiState {
    data class Success(val movies: List<Movie>) : MovieListUiState
    object Error : MovieListUiState
    object Loading : MovieListUiState
}

sealed interface SelectedMovieUiState {
    data class Success(val movie: Movie, val isFavorite: Boolean) : SelectedMovieUiState
    object Error : SelectedMovieUiState
    object Loading : SelectedMovieUiState
}

sealed interface SelectedPersonUiState {
    data class Success(val person: Person, val movies: List<Movie>) : SelectedPersonUiState
    object Error : SelectedPersonUiState
    object Loading : SelectedPersonUiState
}

class MovieDBViewModel(
    private val moviesRepository: MoviesRepository,
    private val savedMoviesRepository: SavedMovieRepository,
    private val topRatedCacheMoviesRepository: TopRatedCacheMoviesRepository,
    private val popularCacheMoviesRepository: PopularCacheMoviesRepository,

): ViewModel() {

    private val _movieListUiState = MutableStateFlow<MovieListUiState>(MovieListUiState.Loading)
    val movieListUiState: StateFlow<MovieListUiState> = _movieListUiState

    var selectedMovieUiState: SelectedMovieUiState by mutableStateOf(SelectedMovieUiState.Loading)
        private set

    var selectedPersonUiState: SelectedPersonUiState by mutableStateOf(SelectedPersonUiState.Loading)
        private set

    init {
        getPopularMovies()
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            _movieListUiState.value = MovieListUiState.Loading
            _movieListUiState.value = try {
                val movies = moviesRepository.getPopularMovies().results
                if (movies.isNotEmpty()){
                    topRatedCacheMoviesRepository.clearCachedMovieList()
                    popularCacheMoviesRepository.clearCachedMovieList()
                    popularCacheMoviesRepository.cacheMovieList(movies)
                    moviesRepository.cancelRefresh()
                    MovieListUiState.Success(movies)
                }
                else{
                    val movies = popularCacheMoviesRepository.getCachedMovieList()
                    moviesRepository.cancelRefresh()
                    MovieListUiState.Success((movies))
                }

            } catch (e: IOException) {
                val movies = popularCacheMoviesRepository.getCachedMovieList()
                if (movies.isNotEmpty()) {
                    MovieListUiState.Success((movies))
                }
                else{
                    moviesRepository.popularNetworkRefresh()
                    MovieListUiState.Error
                }
            } catch (e: HttpException) {
                moviesRepository.popularNetworkRefresh()
                MovieListUiState.Error
            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            _movieListUiState.value = MovieListUiState.Loading
            _movieListUiState.value = try {
                val movies = moviesRepository.getTopRatedMovies().results
                if (movies.isNotEmpty()){
                    popularCacheMoviesRepository.clearCachedMovieList()
                    topRatedCacheMoviesRepository.clearCachedMovieList()
                    topRatedCacheMoviesRepository.cacheMovieList(movies)
                    moviesRepository.cancelRefresh()
                    MovieListUiState.Success((movies))
                }
                else{
                    val movies = topRatedCacheMoviesRepository.getCachedMovieList()
                    moviesRepository.cancelRefresh()
                    MovieListUiState.Success((movies))
                }
            } catch (e: IOException) {
                val movies = topRatedCacheMoviesRepository.getCachedMovieList()
                if (movies.isNotEmpty()) {
                    MovieListUiState.Success((movies))
                }
                else{
                    moviesRepository.topRatedNetworkRefresh()
                    MovieListUiState.Error
                }
            } catch (e: HttpException) {
                moviesRepository.topRatedNetworkRefresh()
                MovieListUiState.Error
            }
        }
    }

    fun getSavedMovies(){
        viewModelScope.launch {
            _movieListUiState.value = MovieListUiState.Loading
            _movieListUiState.value = try {
                MovieListUiState.Success((savedMoviesRepository.getSavedMovies()))
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
        }
    }

    fun saveMovie(movie: Movie) {
        viewModelScope.launch {
            savedMoviesRepository.insertMovie(movie)
            selectedMovieUiState = SelectedMovieUiState.Success(movie, true)
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            savedMoviesRepository.deleteMovie(movie)
            selectedMovieUiState = SelectedMovieUiState.Success(movie, false)
        }
    }

    fun setSelectedMovie(movie: Movie) {
        viewModelScope.launch {
            selectedMovieUiState = SelectedMovieUiState.Loading
            selectedMovieUiState = try {
                SelectedMovieUiState.Success(movie,savedMoviesRepository.getSavedMovies().any { it.id == movie.id })
            } catch (e: IOException) {
                SelectedMovieUiState.Error
            } catch (e: HttpException) {
                SelectedMovieUiState.Error
            }
        }
    }

    fun setSelectedPerson(person: Person) {
        viewModelScope.launch {
            selectedPersonUiState = SelectedPersonUiState.Loading
            selectedPersonUiState = try {
                SelectedPersonUiState.Success(person, moviesRepository.getMoviesByIds(person.known_for).results)
            } catch (e: IOException) {
                SelectedPersonUiState.Error
            } catch (e: HttpException) {
                SelectedPersonUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieDBApplication)
                val moviesRepository = application.container.moviesRepository
                val savedMovieRepository = application.container.savedMovieRepository
                val topRatedCacheMoviesRepository = application.container.topRatedCacheMoviesRepository
                val popularCacheMoviesRepository = application.container.popularCacheMoviesRepository
                MovieDBViewModel(
                    moviesRepository = moviesRepository,
                    savedMoviesRepository = savedMovieRepository,
                    topRatedCacheMoviesRepository = topRatedCacheMoviesRepository,
                    popularCacheMoviesRepository = popularCacheMoviesRepository)
            }
        }
    }
}
