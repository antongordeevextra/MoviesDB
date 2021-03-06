package com.example.moviesdb.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.example.moviesdb.data.local.CustomListEntity
import com.example.moviesdb.data.local.MoviesDbDatabase
import com.example.moviesdb.data.remote.DetailMovieApi
import com.example.moviesdb.domain.model.CustomList
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.repository.MainMovieRepository
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.lang.Exception

class MainMovieRepositoryImpl(
    private val api: DetailMovieApi,
    private val db: MoviesDbDatabase
) : MainMovieRepository {

    private val dao = db.dao
    private val daoCustomList = db.daoCustomList

    override suspend fun insertMovie(movieId: Int, listId: Int): Flow<Resource<String>> = flow {
        try {
            val movie = api.getMovieDetails(movieId).toMovieEntity()
            val movieToInsert = movie.copy(
                idList = listId
            )
            dao.insertMovie(movieToInsert)

            emit(Resource.Success("${movie.original_title} added"))

        } catch (error: Throwable) {
            emit(Resource.Error("It`s a problem..."))
        }
    }

    override suspend fun getAllMovies(): Flow<Resource<List<Movie>>> {
        try {
            val movies = dao.getAllMovies().map {
                it.map { movieEntity ->
                    movieEntity.toMovie()
                }
            }.map {
                Resource.Success(it)
            }.flowOn(Dispatchers.Default)

            return movies

        } catch (error: Exception) {
            return flow {
                Resource.Error(error.toString())
            }
        }
    }

    override suspend fun deleteMovie(id: Int) {
        dao.deleteMovie(id)
    }

    override suspend fun insertCustomListItem(listItem: CustomList) {
        daoCustomList.insertListItem(
            listItem.toCustomListEntity()
        )
    }

    override suspend fun getAllListItems(): Flow<Resource<List<CustomList>>> {
        try {
            val lists = daoCustomList.getAllListItems().map {
                it.map { customListEntity ->
                customListEntity.toCustomList()

                }
            }.map {
                Resource.Success(it)
            }.flowOn(Dispatchers.Default)



            return lists

        } catch (error: Exception) {
            return flow {
                Resource.Error(error.toString())
            }
        }
    }

    override suspend fun deleteList(listId: Int) {
        db.withTransaction {
            dao.deleteMoviesWithList(listId)
            daoCustomList.deleteList(listId)
        }
    }
}