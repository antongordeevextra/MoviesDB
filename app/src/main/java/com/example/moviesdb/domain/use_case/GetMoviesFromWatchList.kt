package com.example.moviesdb.domain.use_case

import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.repository.MainMovieRepository
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow

class GetMoviesFromWatchList(private val repository: MainMovieRepository) {

    suspend operator fun invoke(): Flow<Resource<List<Movie>>> =
        repository.getAllMovies()
}