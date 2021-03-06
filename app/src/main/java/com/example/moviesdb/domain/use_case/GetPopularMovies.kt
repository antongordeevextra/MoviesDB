package com.example.moviesdb.domain.use_case

import androidx.paging.PagingData
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.repository.PopularSearchMoviesRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMovies(private val repository: PopularSearchMoviesRepository) {

    operator fun invoke(): Flow<PagingData<Movie>> =
        repository.getAllPopularMovies()
}