package com.app.jobappication.viewmodel

import android.net.http.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstapp.jetpack1.movieApp.MovieAPIService
import com.firstapp.jetpack1.movieApp.Result
import kotlinx.coroutines.launch
import java.io.IOException

class MovieViewModel : ViewModel() {

    private val _movies =  mutableStateListOf<Result>()

    val movies: List<Result> = _movies
    var totalPage : Long = 0
    var errorMessage: String by mutableStateOf("")
    var isLoading: Boolean by mutableStateOf(false)
    var page : Int = 1
    var sortMenu: Boolean by mutableStateOf(false)
    var sortByBool: Boolean by mutableStateOf(false)
    var sortCriterion: String by mutableStateOf("popularity")
    private val movieApiService = MovieAPIService.getInstance()

    fun fetchMovies() {
        val sortBy = when (sortCriterion) {
            "popularity" -> if (sortByBool) "popularity.asc" else "popularity.desc"
            "release_date" -> if (sortByBool) "release_date.asc" else "release_date.desc"
            "vote_average" -> if (sortByBool) "vote_average.asc" else "vote_average.desc"
            else -> "popularity.desc"
        }

        val apiKey = "8eb5c5c9d3607b11e9a67289c5b655ac"
        viewModelScope.launch {
            isLoading = true
            try {
                _movies.clear()
                val response = movieApiService.getMovies(apiKey, page, sortBy)
                _movies.addAll(response.results)
                totalPage = response.total_pages
            } catch (e: IOException) {
                errorMessage = "Network error: ${e.message}"
            } catch (e: HttpException) {
                errorMessage = "HTTP error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun updateSortCriterion(criterion: String) {
        if (sortCriterion == criterion) {
            sortByBool = !sortByBool
        } else {
            sortCriterion = criterion
            sortByBool = false
        }
        fetchMovies()
    }
}
