package com.example.app.data.repository.task

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.app.data.ErrorMapper
import com.example.app.data.ErrorResponse
import com.example.app.data.datasource.task.TaskLocalDataSource
import com.example.app.data.datasource.task.TaskRemoteDataSource
import com.example.app.domain.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
  private val localDataSource: TaskLocalDataSource,
  private val remoteDataSource: TaskRemoteDataSource,
  private val taskMapper: TaskMapper,
  private val errorMapper: ErrorMapper,
) {
  private val tasksFlow = MutableSharedFlow<Either<ErrorResponse, List<Task>>?>()

  fun getTasks(
    viewModelScope: CoroutineScope
  ): Flow<Either<ErrorResponse, List<Task>>?> {
    viewModelScope.launch {
      val dbTasks = localDataSource.getAllTasks()
      if (dbTasks.isNotEmpty()) {
        tasksFlow.emit(
          dbTasks.map(taskMapper::entityToDomain).right()
        )
      }

      remoteDataSource.fetchTasks()
        .map { tasks ->
          tasks.map(taskMapper::dtoToDomain)
        }
        .mapLeft(errorMapper::map)
        .onRight { tasks ->
          tasksFlow.emit(tasks.right())
          localDataSource.insertAllTasks(
            tasks.map(taskMapper::domainToEntity)
          )
        }
        .onLeft { error ->
          if (dbTasks.isEmpty()) {
            tasksFlow.emit(error.left())
          } else {
            when (error) {
              ErrorResponse.NoInternet -> {
                // DO NOTHING:
                // We do not emit NoInternet errors so the user can use the app offline
              }

              ErrorResponse.Other -> tasksFlow.emit(error.left())
            }
          }
        }
    }

    return tasksFlow
  }

  suspend fun refresh(showLoading: Boolean): Either<ErrorResponse, List<Task>> {
    if (showLoading) {
      tasksFlow.emit(null)
    }
    val res = remoteDataSource.fetchTasks()
      .mapLeft(errorMapper::map)
      .map { tasks ->
        tasks.map(taskMapper::dtoToDomain)
      }
      .onRight { tasks ->
        localDataSource.insertAllTasks(
          tasks.map(taskMapper::domainToEntity)
        )
      }
    tasksFlow.emit(res)
    return res
  }
}