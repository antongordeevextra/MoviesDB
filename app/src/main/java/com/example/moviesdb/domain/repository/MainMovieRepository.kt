package com.example.moviesdb.domain.repository

import com.example.moviesdb.data.local.MovieEntity
import com.example.moviesdb.domain.model.CustomList
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow


interface MainMovieRepository {

    suspend fun insertMovie(movieId: Int, listId: Int): Flow<Resource<String>>

    suspend fun getAllMovies(): Flow<Resource<List<Movie>>>

    suspend fun deleteMovie(id: Int)

    suspend fun insertCustomListItem(listItem: CustomList)

    suspend fun getAllListItems(): Flow<Resource<List<CustomList>>>

    suspend fun deleteList(listId: Int)
}