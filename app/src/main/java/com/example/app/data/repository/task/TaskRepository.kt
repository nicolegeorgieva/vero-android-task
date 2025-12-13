package com.example.app.data.repository.task

import arrow.core.Either
import arrow.core.right
import com.example.app.data.ErrorMapper
import com.example.app.data.ErrorResponse
import com.example.app.data.database.task.TaskEntity
import com.example.app.data.datasource.task.TaskLocalDataSource
import com.example.app.data.datasource.task.TaskRemoteDataSource
import com.example.app.domain.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskRepository @Inject constructor(
  private val localDataSource: TaskLocalDataSource,
  private val remoteDataSource: TaskRemoteDataSource,
  private val taskMapper: TaskMapper,
  private val errorMapper: ErrorMapper,
  private val coroutineScope: CoroutineScope,
) {
  fun getTasks(): Flow<Either<ErrorResponse, List<Task>>> {
    return localDataSource.getAllTasks()
      .filter { taskEntities ->
        taskEntities.isNotEmpty()
      }
      .map<List<TaskEntity>, Either<ErrorResponse, List<Task>>> { tasks ->
        tasks.map { taskEntity ->
          taskMapper.entityToDomain(taskEntity)
        }.right()
      }.onStart {
        coroutineScope.launch {
          val tasksRes = remoteDataSource.fetchTasks()
            .mapLeft(errorMapper::map)
            .map { tasks ->
              tasks.map { taskDto ->
                taskMapper.dtoToDomain(taskDto)
              }
            }.onRight { tasksDomain ->
              localDataSource.insertAllTasks(
                tasksDomain.map { taskDomain ->
                  taskMapper.domainToEntity(taskDomain)
                }
              )
            }
          emit(tasksRes)
        }
      }
  }

  fun refreshTasks() {

  }
}